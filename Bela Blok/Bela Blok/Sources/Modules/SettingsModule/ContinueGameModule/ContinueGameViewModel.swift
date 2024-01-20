
import AVFoundation
import CodeScanner
import CoreImage.CIFilterBuiltins
import Foundation
import SwiftUI

class ContinueGameViewModel: ObservableObject {
    @Published var qrCode: UIImage?
    @Published var errorGeneratingCode: Error?
    private var compressionAlgorithm: NSData.CompressionAlgorithm = .zlib
    
    @MainActor
    private func generateQRCode(from string: String) async -> UIImage? {
        let context = CIContext()
        let filter = CIFilter.qrCodeGenerator()
        let data = string.data(using: .utf8)
        filter.setValue(data, forKey: "inputMessage")
        if let qrCodeImage = filter.outputImage {
            if let qrCodeCGImage = context.createCGImage(qrCodeImage, from: qrCodeImage.extent) {
                return UIImage(cgImage: qrCodeCGImage)
            }
        }
        return nil
    }
    
    @MainActor
    func setQRCode() async {
        if qrCode == nil {
            Task {
                do {
                    let jsonEncoder = JSONEncoder()
                    let json = try jsonEncoder.encode(AppState.shared.currentGame)
                    let compressedJSON = try NSData(data: json).compressed(using: compressionAlgorithm)
                    await qrCode = generateQRCode(from: Data(referencing: compressedJSON).base64EncodedString())
                } catch (let error) {
                    errorGeneratingCode = error
                }
            }
        }
    }
    
    func handleQRCodeRead(scanResult: ScanResult, completion: @escaping () -> Void) {
        do {
            let jsonDecoder = JSONDecoder()
            let dataDecompressed = Data(
                referencing: try NSData(base64Encoded: scanResult.string)?.decompressed(using: compressionAlgorithm) ?? NSData()
            )
            let game = try jsonDecoder.decode([Game].self, from: dataDecompressed)
            
            AppState.shared.currentGame = game
            if let lastDealer = game.last?.dealer {
                AppState.shared.currentDealer = lastDealer
            }
            
            completion()
        } catch (let error) {
            errorGeneratingCode = error
        }
    }
}

