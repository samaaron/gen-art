(ns gen-art.three-two-b
  (:use [rosado.processing]
        [rosado.processing.applet]))

;; Example from Section 3.2, page 56
;; =================================

;; void setup() {
;;   size(500, 100);
;;   background(255);
;;   strokeWeight(5);
;;   smooth();
;;   stroke(0, 30);
;;   line(20, 50, 480, 50);
;;   stroke(20, 50, 70);

;;   float xstep = 10;
;;   float ystep = 10;
;;   float lastx = 20;
;;   float lasty = 50;
;;   float y = 50;
;;   int borderx = 20;
;;   int bordery = 10;
;;   for(int x = borderx; x <= width - borderx; x += xstep){
;;     ystep = random(20) - 10; //range -10 to 10
;;     y += ystep;
;;     line(x, y, lastx, lasty);
;;     lastx = x;
;;     lasty = y;
;;   }
;; }

(defn rand-walk-ys
  [seed]
  (lazy-seq (cons seed (rand-walk-ys (+ seed (- (rand 20) 10))))))

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
        start-y   (/ (height) 2)
        end-x     (- (width) border-x)
        end-y     start-y
        x-mids    (range (+ border-x step) (- end-x step) step)
        xy-mids   (flatten (map (fn [x y]  [x y x y])
                                x-mids (rand-walk-ys 10)))
        xys       (flatten [border-x start-y xy-mids end-x end-y])
        line-args (partition 4 xys)]
    (dorun (map #(apply line %) line-args))))

(defapplet example
  :title "Random Walk Scribble"
  :setup setup
  :size [500 100])

(run example :interactive)
;;(stop example)
