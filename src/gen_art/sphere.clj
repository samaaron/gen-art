(ns gen-art.sphere
  (:use [rosado.processing]
        [rosado.processing.applet]))

;; Section 5.3.1, p94

;; import processing.opengl.*;

;; void setup() {
;;   size(500, 300, OPENGL);
;;   sphereDetail(40);

;;   translate(width/2, height/2.0);
;;   sphere(100);
;; }

(defn setup []
  (size 500 300 OPENGL)
  (smooth)
  (sphere-detail 100)
  (translate (/ (width) 2) (/ (height) 2) 0)
  (sphere 100))

(defapplet example
  :title "3D Sphere"
  :setup setup
  :size [500 300])

(run example :interactive)
