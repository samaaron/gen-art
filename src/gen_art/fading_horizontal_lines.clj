(ns gen-art.fading-horizontal-lines
  (:use [rosado.processing]
        [rosado.processing.applet]))

;; Example from Section 2.4.3, page 39

;; void setup() {
;;   size(500, 300);
;;   background(180);
;;   strokeWeight(4);
;;   strokeCap(SQUARE);
;;   for(int h = 10; h <= (height - 15); h+=10){
;;     stroke(0, 255-h);
;;     line(10, h, width - 20, h);
;;     stroke(255, h);
;;     line(10, h+4, width - 20, h+4);
;;   }
;; }

(defn draw-line
  "Draws a horizontal line on the canvas at height hq"
  [h]
  (stroke 0 (- 255 h))
  (line 10 h (- (width) 20) h)
  (stroke 255 h)
  (line 10 (+ h 4) (- (width) 20) (+ h 4)))

(defn setup []
  (size 500 300)
  (background 180)
  (stroke-weight 4)
  (stroke-cap :square)
  (let [line-heights (range 10 (- (height) 15) 10)]
    (dorun (map draw-line line-heights))))

(defapplet example
  :title "Fading Horizontal Lines"
  :setup setup
  :size [500 300])

(run example :interactive)
;;(stop example)
