(ns gen-art.growing-circle
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [seq->stream range-incl]]))


;; Listing 2.1
;; ===========

;; Page 28

;; int diam = 10;
;; float centX, centY;

;; void setup() {
;;   size(500, 300);
;;   frameRate(24);
;;   smooth();
;;   background(180);
;;   centX = width/2;
;;   centY = height/2;
;;   stroke(0);
;;   strokeWeight(5);
;;   fill(255, 50);
;; }

;; void draw() {
;;   if(diam <= 400) {
;;     background(180);
;;     ellipse(centX, centY, diam, diam);
;;     diam += 10;
;;   }
;; }

(defn setup []
  (size 500 300)
  (frame-rate 24)
  (smooth)
  (background 180)
  (stroke 0)
  (stroke-weight 5)
  (fill 255 25)
  (let [diams (range-incl 10 400 10)]
    (set-state! :diam (seq->stream diams)
                :cent-x (/ (width) 2)
                :cent-y (/ (height) 2))))

(defn draw []
  (let [cent-x (state :cent-x)
        cent-y (state :cent-y)
        diam   ((state :diam))]
    (background 180)
    (ellipse cent-x cent-y diam diam)))

(defapplet example
  :title "Growing circle"
  :setup setup
  :draw draw
  :size [500 300])

(run example :interactive)
;;(stop example)
