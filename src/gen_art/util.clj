(ns gen-art.util
  (:use [rosado.processing]))

(defn line-join-points
  "takes two lists (possibly infinite lazy seqs) of x and y coords and
  creates a lazy list of line args (vectors of 4 elements) suitable
  for use with the line fn.

   (join-points [1 2 3] [4 5 6]) ;=> ([1 4 2 5] [2 5 3 6])"
  [xs ys]
  (lazy-seq
   (if (and (next xs) (next ys))
     (cons [(first xs) (first ys) (second xs) (second ys)]
           (join-points (next xs) (next ys)))
     [])))

(defn scaled-perlin-noise
  "Generate a lazy infinite sequence of perlin noise values starting from
  the specified seed with incr added to the seed for each successive value."
  [seed incr]
  (lazy-seq (cons (noise seed) (scaled-perlin-noise (+ seed incr) incr))))

(defn mul-add
  "Generate a lazy sequence of values from seq s which mulplies each
  val in s by mul and then adds add."
  [s mul add]
  (map #(+ add (* mul %)) (seq s)))

(defn range-incl
  "Returns a lazy seq of nums from start (inclusive) to end
  (inclusive), by step, where start defaults to 0, step to 1, and end
  to infinity."
  ([] (range 0 Double/POSITIVE_INFINITY 1))
  ([end] (range 0 end 1))
  ([start end] (range start end 1))
  ([start end step]
   (lazy-seq
    (let [b (chunk-buffer 32)
          comp (if (pos? step) <= >=)]
      (loop [i start]
        (if (and (< (count b) 32)
                 (comp i end))
          (do
            (chunk-append b i)
            (recur (+ i step)))
          (chunk-cons (chunk b)
                      (when (comp i end)
                        (range-incl i end step)))))))))
