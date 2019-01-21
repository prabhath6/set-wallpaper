(ns set-wallpaper.file-operations)

(defn get-directory
  "Builds the directory path from home."
  [folder-name]
  (-> (System/getProperties)
      (.get "user.home")
      (str (System/getProperty "file.separator") folder-name)))

(defn folder-exists
  "Checks if the given folder exists in home directory"
  [folder-name]
  (-> folder-name
      (get-directory)
      (java.io.File.)
      (.isDirectory)))

(defn create-directory
  "Creates the given directory"
  [dir-name]
  (-> dir-name
      (java.io.File.)
      (.mkdir)))

(defn get-file-name
  "Creates file name based on current time."
  [file-extension]
  (let [current-date (java.util.Date.)
        date-format "MM-dd-yyyy-HH-mm-ss"
        formatted-date (.format (java.text.SimpleDateFormat. date-format) current-date)]
    (str formatted-date "." file-extension)))

(defn get-file-full-path
  "Returns the full path"
  [folder-path file-name]
  (str folder-path (System/getProperty "file.separator") file-name))

(defn save-image-to-file
  "Save the image to the file"
  [url filename]
  (with-open [in (clojure.java.io/input-stream url)
              out (clojure.java.io/output-stream filename)]
    (clojure.java.io/copy in out)))

(defn set-background
  "Set given image as background"
  [file-path]
  (let [file-uri (str "file://" file-path)
        command "gsettings set org.gnome.desktop.background picture-uri "]
    (. (Runtime/getRuntime) exec (str command file-uri))))
