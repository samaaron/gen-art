(ns gen-art.three-one
  (:use [rosado.processing]
        [rosado.processing.applet]))

(defn setup []
  (size 500 300)
  (frame-rate 24)
  (smooth)
  (background 180)
  (stroke 0)
  (stroke-weight 1)
  (no-fill)
  (set-state! :diam (atom 10)
              :cent-x (/ (width) 2)
              :cent-y (/ (height) 2)))

(defn draw []
  (let [cent-x (state :cent-x)
        cent-y (state :cent-y)
        diam   (state :diam)]
    (when (<= @diam 400)
      (ellipse cent-x cent-y @diam @diam)
      (swap! diam + 10))))

(defapplet example
  :title "Concentric Circles"
  :setup setup
  :draw draw
  :size [500 300])

(run example :interactive)
;;(stop example)
