(ns gen-art.dotted-circle
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [range-incl line-join-points]]))

;; Listing 4.1, page 68

;; void setup() {
;;   size(500,300);
;;   background(255);
;;   strokeWeight(5);
;;   smooth();
;;   float radius = 100;
;;   int centX = 250;
;;   int centY = 150;
;;   stroke(0, 30);
;;   noFill();
;;   ellipse(centX,centY,radius*2,radius*2);
;;   stroke(20, 50, 70);
;;   float x, y;
;;   float lastx = -999;
;;   float lasty = -999;
;;   for (float ang = 0; ang <= 360; ang += 5) {
;;     float rad = radians(ang);
;;     x = centX + (radius * cos(rad));
;;     y = centY + (radius * sin(rad));
;;     point(x,y);
;;   }
;; }

(defn setup []
  (size 500 300)
  (background 255)
  (stroke-weight 5)
  (smooth)
  (let [radius    100
        cent-x    250
        cent-y    150
        rads      (map radians (range-incl 0 360 5))
        xs        (map #(+ cent-x (* radius (cos %))) rads)
        ys        (map #(+ cent-y (* radius (sin %))) rads)]
    (stroke 0 30)
    (no-fill)
    (ellipse cent-x cent-y (* radius 2) (* radius 2))
    (stroke 20 50 70)
    (dorun (map point xs ys))))

(defapplet example
  :title "Dotted Circle"
  :setup setup
  :size [500 300])

(run example :interactive)
;;(stop example)
