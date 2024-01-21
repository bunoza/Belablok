import Lottie
import SwiftUI

struct LottieView: UIViewRepresentable {
    var name: String
    var loopMode: LottieLoopMode
    var animationView: LottieAnimationView

    init(name: String, loopMode: LottieLoopMode = .playOnce) {
        self.name = name
        self.loopMode = loopMode
        animationView = LottieAnimationView(name: name)
    }

    func makeUIView(context _: UIViewRepresentableContext<LottieView>) -> UIView {
        let view = UIView(frame: .zero)

        animationView.contentMode = .scaleToFill
        animationView.loopMode = loopMode
        animationView.play()

        animationView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(animationView)

        NSLayoutConstraint.activate([
            animationView.heightAnchor.constraint(equalTo: view.heightAnchor),
            animationView.widthAnchor.constraint(equalTo: view.widthAnchor),
        ])

        return view
    }

    func updateUIView(_: UIView, context _: UIViewRepresentableContext<LottieView>) {}
}
