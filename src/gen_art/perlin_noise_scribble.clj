(ns gen-art.perlin-noise-scribble
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [line-join-points range-incl]]))

;; Listing 3.1, page 59
;; ====================

;; void setup() {
;;  size(500, 100);
;;  background(255);
;;  strokeWeight(5);
;;  smooth();

;;  stroke(0, 30);
;;  line(20, 50, 480, 50);

;;  stroke(20, 50, 70);
;;  int step = 10;
;;  float lastx = -999;
;;  float lasty = -999;
;;  float ynoise = random(10);
;;  float y;
;;  for (int x = 20 ; x <= 480 ; x += step){
;;    y = 10 + noise(ynoise) * 80;
;;    if(lastx > -999) {
;;      line(x, y, lastx, lasty);
;;    }
;;    lastx = x;
;;    lasty =y;
;;    ynoise += 0.1;
;;  }
;; }

(defn scaled-noise
  [seed]
  (let [mul 80
        add 10]
    (+ add (* mul (noise seed)))))

(defn rand-walk-ys
  [seed]
  (lazy-seq (cons (scaled-noise seed) (rand-walk-ys (+ seed 0.1)))))

(defn setup []
  (size 500 100)
  (background 255)
  (stroke-weight 5)
  (smooth)

  (stroke 0 30)
  (line 20 50 480 50)

  (stroke 20 50 70)
  (let [step      10
        y-noise   (rand 10)
        border-x  20
        xs        (range-incl border-x (- (width) border-x) step)
        ys        (rand-walk-ys y-noise)
        line-args (line-join-points xs ys)]
    (dorun (map #(apply line %) line-args))))

(defapplet example
  :title "Perlin Noise Scribble"
  :setup setup
  :size [500 100])

(run example :interactive)
;;(stop example)
