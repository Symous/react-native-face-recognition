require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-face-recognition"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-face-recognition
                   DESC
  s.homepage     = "https://github.com/github_account/react-native-face-recognition"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/github_account/react-native-face-recognition.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.vendored_frameworks = "ios/FaceRecognition/Frameworks/FaceSDK/IDLFaceSDK.framework"
  s.resource = "ios/FaceRecognition/Frameworks/FaceSDK/*.{bundle,face-ios}"

  s.dependency "React"
  # ...
  # s.dependency "..."
end

