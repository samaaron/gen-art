(ns gen-art.animated-rotated-lines
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [steps seq->stream range-incl mul-add tap tally]]))

;; Listing 5.4, p91

;; float xstart, xnoise, ystart, ynoise;
;; float xstartNoise, ystartNoise;

;; void setup() {
;;   size(300, 300);
;;   smooth();
;;   background(255);
;;   frameRate(24);

;;   xstartNoise = random(20);
;;   ystartNoise = random(20);

;;   xstart = random(10);
;;   ystart = random(10);
;; }

;; void draw() {
;;   background(255);

;;   xstart += 0.01;
;;   ystart += 0.01;

;;   xstartNoise += 0.01;
;;   ystartNoise += 0.01;
;;   xstart += (noise(xstartNoise) * 0.5) - 0.25;
;;   ystart += (noise(ystartNoise) * 0.5) - 0.25;

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
;;   rotate(noiseFactor * radians(360));
;;   stroke(0, 150);
;;   line(0, 0, 20, 0);
;;   popMatrix();
;; }

(defn draw-point
  [x y noise-factor]
  (push-matrix)
  (translate x y)
  (rotate (* noise-factor (radians 360)))
  (stroke 0 150)
  (line 0 0 20 0)
  (pop-matrix))

(defn draw-all-points [x-start y-start]
  (let [step-size 15
        x-idxs    (range-incl 0 (/ (width) step-size))
        y-idxs    (range-incl 0 (/ (height) step-size))]
    (dorun
     (for [x-idx x-idxs
           y-idx y-idxs]
       (let [x (* step-size x-idx)
             y (* step-size y-idx)
             x-noise (mul-add x-idx 0.1 x-start)
             y-noise (mul-add y-idx 0.1 y-start)]
         (draw-point x y (noise x-noise y-noise)))))))

(defn starts-seq
  []
  (let [noise-steps (steps (random 20) 0.01)
        noises      (map noise noise-steps)
        noises      (mul-add noises 0.5 -0.25)
        noise-tally (tally noises)]
    (map +
         (steps (random 10) 0.01)
         noise-tally)))

(defn setup []
  (size 300 300)
  (smooth)
  (background 255)
  (frame-rate 24)

  (let [x-starts      (starts-seq)
        y-starts      (starts-seq)
        starts-str    (seq->stream (map list x-starts y-starts))]
    (set-state! :starts-str starts-str)))

(defn draw []
  (background 255)
  (let [[x-start y-start] ((state :starts-str))]
    (draw-all-points x-start y-start)))

(defapplet example
  :title "Animated Rotated Lines"
  :setup setup
  :draw draw
  :size [300 300])

(run example :interactive)
