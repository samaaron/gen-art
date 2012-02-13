(ns gen-art.animated-fluffy-clouds
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [steps seq->stream range-incl mul-add tap]]))

;; Listing 5.3, p89

;; float xstart, xnoise, ystart, ynoise;

;; void setup() {
;;   size(300, 300);
;;   smooth();
;;   background(0);
;;   frameRate(24);

;;   xstart = random(10);
;;   ystart = random(10);
;; }

;; void draw() {
;;   background(0);

;;   xstart += 0.01;
;;   ystart += 0.01;

;;   xnoise = xstart;
;;   ynoise = ystart;

;;   for(int y = 0; y <= height; y+=5){
;;     ynoise += 0.1;
;;     xnoise = xstart;
;;     for(int x = 0; x <= width; x+= 5){
;;       xnoise += 0.1;
;;       drawPoint(x, y, noise(xnoise, ynoise));
;;     }
;;   }
;; }

;; void drawPoint(float x, float y, float noiseFactor) {
;;   pushMatrix();
;;   translate(x, y);
;;   rotate(noiseFactor * radians(540));
;;   noStroke();
;;   float edgeSize = noiseFactor * 35;
;;   float grey = 150 + (noiseFactor * 120);
;;   float alph = 150 + (noiseFactor * 120);
;;   fill(grey, alph);
;;   ellipse(0, 0, edgeSize, edgeSize/2);
;;   popMatrix();
;; }

(defn draw-point
  [x y noise-factor]
  (push-matrix)
  (translate x y)
  (rotate (* noise-factor (radians 540)))
  (let [edge-size (* noise-factor 35)
        grey (mul-add noise-factor 120 150)
        alph (mul-add noise-factor 120 150)]
    (no-stroke)
    (fill grey alph)
    (ellipse 0 0 edge-size (/ edge-size 2))
    (pop-matrix)))

(defn draw-all-points [x-shift y-shift]
  (dorun
   (for [x (range-incl 0 (width) 5)
         y (range-incl 0 (height) 5)]
     (let [x-noise (mul-add x 0.01 x-shift)
           y-noise (mul-add y 0.01 y-shift)]
       (draw-point x y (noise x-noise y-noise))))))

(defn draw []
  (background 0)
  (let [x-shift ((state :x-shifts))
        y-shift ((state :y-shifts))]
    (draw-all-points x-shift y-shift)))

(defn setup []
  (size 300 300)
  (smooth)
  (background 0)
  (frame-rate 24)

  (let [x-shifts     (steps (random 10) 0.01)
        y-shifts     (steps (random 10) 0.01)
        x-shifts-str (seq->stream x-shifts)
        y-shifts-str (seq->stream y-shifts)]

    (set-state! :x-shifts x-shifts-str
                :y-shifts y-shifts-str)))

(defapplet example
  :title "Animtied Fluffy Clouds"
  :setup setup
  :draw draw
  :size [300 300])

(run example :interactive)
