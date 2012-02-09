(ns gen-art.scratch
  (:use [rosado.processing]
        [rosado.processing.applet]))

(defn setup []
  (size 500 100)

  )

(defn draw []

  )

(defapplet example
  :title "Scratch"
  :setup setup
  :draw draw
  :size [500 100])

(run example :interactive)
