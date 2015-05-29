(ns clojure-getting-started.normalize)

(defn normalize
  "Normalize a sequence, vector or map of numbers. First level values only, nested sequences may have unexpected results.
  Default normal-high/low between 1 and 0."
  {:added "0.0"}
  ([abnormal-numbers]
   (normalize abnormal-numbers 1 0))

  ([abnormal-numbers normal-high normal-low]
   (if (empty? abnormal-numbers) abnormal-numbers
     (if (map? abnormal-numbers)
       ; normalize the values and return mapped to appropriate keys
       (zipmap ; TODO optimize?
        (keys abnormal-numbers )
        (partition (/ (count (flatten (vals abnormal-numbers))) (count (keys abnormal-numbers)) )
                   (normalize-seq (flatten (vals abnormal-numbers)) normal-high normal-low)))
       ; return normalized sequence
       (clojure-getting-started.normalize/normalize-seq abnormal-numbers normal-high normal-low)))))


(defn- normalize-seq
  ([values normal-high normal-low]

   (def data-high (apply max values)) ; TODO optimize? get data-max and data-min in same loop
   (def data-low (apply min values))

     (loop [result []
            cnt 0]
       (if (>= cnt (count values))
         result
         (recur (conj result
            ; feature scaling math
                (+ normal-low
                   (* (/ (- (nth values cnt) data-low) (- data-high data-low))
                      (- normal-high normal-low))))
         (inc cnt))))))
