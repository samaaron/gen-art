(ns gen-art.sine-wave-with-noise
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [line-join-points mul-add]]))

;; Listing 3.2, page 60

;; void setup() {
;;  size(500, 100);
;;  background(255);

;;  float xstep = 1;
;;  float lastx = -999;
;;  float lasty = -999;
;;  float angle = 0;
;;  float y = 50;
;;  for(int x=20; x<=480; x+=xstep){
;;    float rad = radians(angle);
;;    y = 50 + (pow(sin(rad), 3) * noise(rad*2) * 30)
;;    if(lastx > -999) {
;;      line(x, y, lastx, lasty);
;;    }
;;    lastx = x;
;;    lasty = y;
;;    angle++;
;;  }
;; }


(defn setup []
  (size 500 100)
  (background 255)
  (let [xs        (range 20 480 1)
        rads      (map radians (range))
        ys        (map sin rads)
        ys        (map #(pow % 3) ys)
        ys        (map (fn [y rad] (* 30 y (noise (* 2 rad)))) ys rads)
        scaled-ys (mul-add ys 1 50)

        line-args (line-join-points xs scaled-ys)]
    (dorun (map #(apply line %) line-args))))

(defapplet example
  :title "Sine Wave with Noise"
  :setup setup
  :size [500 100])

(run example :interactive)
;;(stop example)
