(ns gen-art.noise-grid
    (:use [rosado.processing]
          [rosado.processing.applet]
          [gen-art.util :only [range-incl]]))

;; Listing 5.1, p84

;; void setup() {
;;   size(300,300);
;;   smooth();
;;   background(255);
;;   float xstart = random(10);
;;   float xnoise = xstart;
;;   float ynoise = random(10);
;;   for(int y=0;y<=height;y+=1){
;;     ynoise += 0.01;
;;     xnoise = xstart;
;;     for(int x=0;x<=width;x+=1){
;;       xnoise += 0.01;
;;       int alph = int(noise(xnoise, ynoise) * 255);
;;       stroke(0, alph);
;;       line(x,y, x+1, y+1);
;;     }
;;   }
;; }

(defn setup []
  (size 300 300)
  (smooth)
  (background 255)
  (dorun
   (for [y (range-incl (height))
         x (range-incl (width))]
     (do
       (let [x-noise (* x 0.01)
             y-noise (* y 0.01)
             alph    (* 255 (noise x-noise y-noise))]
         (stroke-int 0 alph)
         (line x y (inc x) (inc y)))))))

(defapplet example
  :title "2D Noise Grid"
  :setup setup
  :size [300 300])

(run example :interactive)
