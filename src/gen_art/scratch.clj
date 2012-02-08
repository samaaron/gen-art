(ns gen-art.scratch
  (:use [rosado.processing]
        [rosado.processing.applet]))

(defn setup []
  (size 500 300)
  (let [c (color 102 102 0)]
    (fill-int c)
    (no-stroke)
    (rect 30 20 55 55)))

(defn draw []
  )


(defapplet example
  :title "Scratch"
  :setup setup
;;  :draw draw
  :size [500 300])

(run example :interactive)
