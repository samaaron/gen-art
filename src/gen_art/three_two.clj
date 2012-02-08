(ns gen-art.three-two
  (:use [rosado.processing]
        [rosado.processing.applet]))

;; Example from Section 3.2, page 55
;; =================================

;; void setup() {
;;   size(500, 100);
;;   background(255);
;;   strokeWeight(5);
;;   smooth();
;;   stroke(0, 30);
;;   line(20, 50, 480, 50);
;;   stroke(20, 50, 70);

;;   int step = 10;
;;   float lastx = -999;
;;   float lasty = -999;
;;   float y = 50;
;;   int borderx = 20;
;;   int bordery = 10;
;;   for(int x = borderx; x <= width - borderx; x += step){
;;     y = bordery + random(height - 2* bordery);
;;     if(lastx > -999) {
;;       line(x, y, lastx, lasty);
;;     }
;;   lastx = x;
;;   lasty = y;
;;   }
;; }

(defn rand-y
  [border-y]
  (+ border-y (rand (- (height) (* 2 border-y)))))

(defn setup []
  (size 500 100)
  (background 255)
  (stroke-weight 5)
  (smooth)
  (stroke 0 30)
  (line 20 50 480 50)

  (stroke 20 50 70)
  (let [step      10
        border-x  20
        border-y  10
        start-y   (rand-y border-y)
        end-x     (- (width) border-x)
        end-y     (rand-y border-y)
        x-mids    (range (+ border-x step) (- end-x step) step)
        xy-mids   (flatten (map (fn [x] (let [y (rand-y border-y)]
                                         [x y x y]))
                                x-mids))
        xys       (flatten [border-x start-y xy-mids end-x end-y])
        line-args (partition 4 xys)]
    (dorun (map #(apply line %) line-args))))


(defapplet example
  :title "Random Scribble"
  :setup setup
  :size [500 100])

(run example :interactive)
;;(stop example)
