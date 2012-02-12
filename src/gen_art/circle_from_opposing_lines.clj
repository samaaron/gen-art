(ns gen-art.circle-from-opposing-lines
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [range-incl line-join-points]]))

;; Section 4.2, page 76

;; void setup() {
;;   size(500,300);
;;   background(255);
;;   strokeWeight(0.5);
;;   smooth();
;;   float radius = 130;
;;   int centX = 250;
;;   int centY = 150;
;;   stroke(20, 50, 70);
;;   float x1, y1, x2, y2, opprad;
;;   float lastx = -999;
;;   float lasty = -999;
;;   for (float ang = 0; ang <= 360; ang += 1) {
;;     float rad = radians(ang);
;;     x1 = centX + (radius * cos(rad));
;;     y1 = centY + (radius * sin(rad));
;;     opprad = rad + PI;
;;
;;     x2 = centX + (radius * cos(opprad));
;;     y2 = centY + (radius * sin(opprad));
;;     line(x1,y1, x2, y2);
;;   }
;; }

(defn setup []
  (size 500 300)
  (background 255)
  (stroke-weight 0.5)
  (smooth)
  (no-fill)
  (stroke 20 50 70)
  (let [radius   130
        cent-x   250
        cent-y   150
        angles   (range-incl 0 360)
        rads     (map radians angles)
        opp-rads (map + rads (repeat PI))

        x1s      (map #(+ cent-x (* radius (cos %))) rads)
        y1s      (map #(+ cent-y (* radius (sin %))) rads)
        x2s      (map #(+ cent-x (* radius (cos %))) opp-rads)
        y2s      (map #(+ cent-y (* radius (sin %))) opp-rads)]
    (doall (map line x1s y1s x2s y2s))))


(defapplet example
  :title "Circle from Opposing Lines"
  :setup setup
  :size [500 300])

(run example :interactive)
;;(stop example)
