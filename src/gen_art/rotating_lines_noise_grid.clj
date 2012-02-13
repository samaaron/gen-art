(ns gen-art.rotating-lines-noise-grid
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [range-incl mul-add]]))

;; Section 5.12 (Figure 5.3) p86

;; float xstart, xnoise, ynoise;
;;
;; void setup() {
;;   size(300, 300);
;;   smooth();
;;   background(255);
;;   xstart = random(10);
;;   xnoise = xstart;
;;   ynoise = random(10);
;;   for(int y = 0; y <= height; y+=5) {
;;     ynoise += 0.1;
;;     xnoise = xstart;
;;     for(int x = 0; x <= width; x+=5) {
;;       xnoise += 0.1;
;;       drawPoint(x, y, noise(xnoise, ynoise));
;;     }
;;   }
;; }

;; void drawPoint(float x, float y, float noiseFactor) {
;;   pushMatrix();
;;   translate(x,y);
;;   rotate(noiseFactor * radians(360));
;;   stroke(0, 150);
;;   line(0,0,20,0);
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

(defn draw-squares
  [x-start y-start]
  (dorun
   (for [y (range-incl 0 (height) 5)
         x (range-incl 0 (width) 5)]
     (let [x-noise (mul-add x 0.01 x-start)
           y-noise (mul-add y 0.01 y-start)
           alph    (* 255 (noise x-noise y-noise))]
       (draw-point x y (noise x-noise y-noise))))))

(defn setup []
  (size 300 300)
  (smooth)
  (background 255)
  (draw-squares (random 10) (random 10)))

(defapplet example
  :title "Rotating Lines 2D Noise Grid"
  :setup setup
  :size [300 300])

(run example :interactive)
