(ns gen-art.wave-clock
  (:use [rosado.processing]
        [rosado.processing.applet]
        [gen-art.util :only [steps mul-add cycle-between seq->stream]]))

;; Listing 4.6, p79

;; float _angnoise, _radiusnoise;
;; float _xnoise, _ynoise;
;; float _angle = -PI/2;
;; float _radius;
;; float _strokeCol = 254;
;; int _strokeChange = -1;

;; void setup() {
;;   size(500, 300);
;;   smooth();
;;   frameRate(30);
;;   background(255);
;;   noFill();
;;   _angnoise = random(10);
;;   _radiusnoise = random(10);
;;   _xnoise = random(10);
;;   _ynoise = random(10);
;; }

;; void draw() {
;;   _radiusnoise += 0.005;
;;   _radius = (noise(_radiusnoise) * 550) +1;
;;   _angnoise += 0.005;
;;   _angle += (noise(_angnoise) * 6) - 3;
;;   if (_angle > 360) { _angle -= 360; }
;;   if (_angle < 0) { _angle += 360; }
;;   _xnoise += 0.01;
;;   _ynoise += 0.01;
;;   float centerX = width/2 + (noise(_xnoise) * 100) - 50;
;;   float centerY = height/2 + (noise(_ynoise) * 100) - 50;
;;   float rad = radians(_angle);
;;   float x1 = centerX + (_radius * cos(rad));
;;   float y1 = centerY + (_radius * sin(rad));
;;   float opprad = rad + PI;
;;   float x2 = centerX + (_radius * cos(opprad));
;;   float y2 = centerY + (_radius * sin(opprad));
;;   _strokeCol += _strokeChange;
;;   if (_strokeCol > 254) { _strokeChange = -1; }
;;   if (_strokeCol < 0) { _strokeChange = 1; }
;;   stroke(_strokeCol, 60);
;;   strokeWeight(1);
;;   line(x1, y1, x2, y2);
;; }

(defn mk-lines-stream
  []
  (let [half-width   (/ (width) 2)
        half-height  (/ (height) 2)
        radius-steps (steps (random 10) 0.005)
        angle-steps  (steps (random 10) 0.005)
        x-steps      (steps (random 10) 0.01)
        x-noises     (map noise x-steps)
        y-steps      (steps (random 10) 0.01)
        y-noises     (map noise y-steps)
        angle-noises (map noise angle-steps)
        angle-noises (mul-add 6 -3 angle-noises)
        angles       (steps (- (/ PI 2)) angle-noises)
        angles       (map #(mod % 360) angles)
        rads         (map radians angles)
        center-xs    (mul-add 100 (- half-width 50) x-noises)
        center-ys    (mul-add 100 (- half-height 50) y-noises)
        radii        (map noise radius-steps)
        radii        (mul-add 550 1 radii)
        x1s          (map (fn [cent-x radius rads] (+ cent-x (* radius (cos rads))))
                          center-xs radii rads)
        y1s          (map (fn [cent-y radius rads] (+ cent-y (* radius (sin rads))))
                          center-ys radii rads)
        opp-rads     (map #(+ PI %) rads)
        x2s          (map (fn [cent-x radius rads] (+ cent-x (* radius (cos rads))))
                          center-xs radii opp-rads)
        y2s          (map (fn [cent-y radius rads] (+ cent-y (* radius (sin rads))))
                          center-ys radii opp-rads)
        lines        (map list x1s y1s x2s y2s)]
    (seq->stream lines)))

(defn mk-cols-stream
  []
  (let [stroke-cols (cycle-between 0 255)]
    (seq->stream stroke-cols)))

(defn setup []
  (size 500 300)
  (smooth)
  (frame-rate 30)
  (background 255)
  (no-fill)
  (stroke-weight 1)
  (set-state! :lines-str (mk-lines-stream)
              :cols-str (mk-cols-stream)))

(defn draw []
  (let [lines-str (state :lines-str)
        cols-str  (state :cols-str)
        line-args (lines-str)
        col       (cols-str)]
    (stroke col 60)
    (apply line line-args)))

(defapplet example
  :title "Wave Clock"
  :setup setup
  :draw draw
  :size [500 300])

(run example :interactive)
;;(stop example)
