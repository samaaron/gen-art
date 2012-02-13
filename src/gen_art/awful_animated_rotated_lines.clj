(ns gen-art.awful-animated-rotated-lines
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [steps seq->stream range-incl mul-add tap]]))

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

;; Truly awful implementation to match the original as closely as possible...

(defn draw-point
  [x y noise-factor]
  (push-matrix)
  (translate x y)
  (rotate (* noise-factor (radians 360)))
  (stroke 0 150)
  (line 0 0 20 0)
  (pop-matrix))


(defn inner-loop
  [x y]
  (when (<= x (width))
    (swap! (state :x-noise) + 0.1)
    (draw-point x y (noise @(state :x-noise) @(state :y-noise)))
    (recur (+ x 5) y)))

(defn outer-loop
  [y]
  (when (<= y (height))
    (swap! (state :y-noise) + 0.1)
    (reset! (state :x-noise) @(state :x-start))
    (inner-loop 0 y)
    (recur (+ y 5))))

(defn draw []
  (background 255)
  (swap! (state :x-start) + 0.01)
  (swap! (state :y-start) + 0.01)

  (swap! (state :x-start-noise) + 0.01)
  (swap! (state :y-start-noise) + 0.01)

  (swap! (state :x-start) + (- (* (noise @(state :x-start-noise)) 0.5) 0.25))
  (swap! (state :y-start) + (- (* (noise @(state :y-start-noise)) 0.5) 0.25))

  (reset! (state :x-noise) @(state :x-start))
  (reset! (state :y-noise) @(state :y-start))

  (outer-loop 0))

(defn setup []
  (size 300 300)
  (smooth)
  (background 255)
  (frame-rate 24)

  (set-state! :x-start-noise (atom (random 20))
              :y-start-noise (atom (random 20))
              :x-start (atom (random 10))
              :y-start (atom (random 10))
              :x-noise (atom nil)
              :y-noise (atom nil)))

(defapplet example
  :title "Awfully Implemented Animated Rotated Lines"
  :setup setup
  :draw draw
  :size [300 300])

(run example :interactive)
