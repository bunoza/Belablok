
import CodeScanner
import SwiftUI

struct ContinueGameView: View {
    @Environment(\.presentations) private var presentations
    @Environment(\.dismiss) private var dismiss
    
    @StateObject private var viewModel: ContinueGameViewModel = .init()
    @State private var selection: ContinueGameOption = .show
    @State private var isShowingCamera: Bool = false
    @State private var isCameraError: ScanError? = nil
    
    var body: some View {
        NavigationView {
            VStack {
                Picker(selection: $selection) {
                    ForEach(ContinueGameOption.allCases) { option in
                        Text(option.description)
                            .tag(option)
                    }
                } label: {
                    EmptyView()
                }
                .pickerStyle(.segmented)
                .padding()
                
                if selection == .show {
                    renderQRCode()
                } else if selection == .scan {
                    renderCodeScanner()
                }
            }
            .toolbar {
                ToolbarItem(placement: .confirmationAction) {
                    Button {
                        dismiss()
                    } label: {
                        Text("Gotovo")
                    }
                }
            }
        }
    }
    
    func renderQRCode() -> some View {
        VStack {
            if let qrCode = viewModel.qrCode {
                Text("Na drugom uređaju upalite Bela Blok, dodirnite *Nastavi na ovom uređaju* i skenirajte QR kod.")
                    .font(.footnote)
                    .padding(.horizontal)
                Spacer()
                Image(uiImage: qrCode)
                    .interpolation(.none)
                    .resizable()
                    .scaledToFit()
                    .padding()
                Spacer()
            } else if viewModel.errorGeneratingCode != nil {
                Spacer()
                ProgressView("Došlo je do greške prilikom generiranja QR koda.")
                    .font(.footnote)
                    .padding(.horizontal)
                Spacer()
            } else {
                Spacer()
                ProgressView("Generiranje QR koda")
                    .progressViewStyle(.circular)
                Spacer()
            }
        }
        .task {
            await viewModel.setQRCode()
        }
    }
    
    func renderCodeScanner() -> some View {
        VStack {
            if isCameraError == nil {
                CodeScannerView(
                    codeTypes: [.qr],
                    scanMode: .once,
                    showViewfinder: true,
                    shouldVibrateOnSuccess: true,
                    isTorchOn: false
                ) { result in
                    switch result {
                    case .success(let success):
                        viewModel.handleQRCodeRead(scanResult: success) {
                            presentations.forEach {
                                $0.wrappedValue = false
                            }
                        }
                    case .failure(let error):
                        self.isCameraError = error
                    }
                }
            } else {
                Spacer()
                Text("Došlo je do greške pri povezivanju s kamerom. Moguće je da trebate dozvoliti pristup kameri.")
                    .font(.footnote)
                    .padding(.horizontal)
                Spacer()
            }
        }
    }
}
