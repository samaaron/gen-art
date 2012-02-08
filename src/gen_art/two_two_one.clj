(ns gen-art.two-two-one
  (:use [rosado.processing]
        [rosado.processing.applet]))

;; Example from Section 2.2.1, page 20
;; ===================================

;; size(500, 300);
;; smooth();
;; background(230, 230, 230);
;; //draw two crossed lines
;; stroke(130, 0, 0);
;; strokeWeight(4);
;; line(width/2 - 70, height/2 - 70, width/2 + 70, height/2 + 70);
;; line(width/2 + 70, height/2 - 70, width/2 - 70, height/2 + 70);
;; //draw a filled circle too
;; fill(255, 150);
;; ellipse(width/2, height/2, 50, 50);

(defn setup []
  (size 500 300)
  (smooth)
  (background 230 230 230)
  (stroke 130, 0 0)
  (stroke-weight 4)
  (let [cross-size      70
        circ-size       50
        canvas-x-center (/ (width) 2)
        canvas-y-center (/ (height) 2)
        left            (- canvas-x-center cross-size)
        right           (+ canvas-x-center cross-size)
        top             (+ canvas-y-center cross-size)
        bottom          (- canvas-y-center cross-size)]
    (line left bottom right top)
    (line right bottom left top)

    (fill 255 150)
    (ellipse canvas-x-center canvas-y-center circ-size circ-size)))

(defapplet ex
  :title "Cross with circle"
  :setup setup
  :size [500 300])

(run ex :interactive)
