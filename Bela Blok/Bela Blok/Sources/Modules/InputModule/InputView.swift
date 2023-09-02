import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) var dismiss
    
    @State private var game: Game = .init()
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea(.container)
                
                List {
                    HStack {
                        UnderlinedTextField(text: game.weScoreString)
                        UnderlinedTextField(text: game.youScoreString)
                    }
                    .listRowSeparator(.hidden)
                    .padding()
                    
                    HStack {
                        Text("Zvanje: \(game.getWeCallsSum())")
                            .font(.subheadline)
                        
                        
                        Button {
                            resetCalls()
                        } label: {
                            Text("Obriši zvanja")
                                .font(.subheadline)
                        }
                        .buttonStyle(.borderedProminent)
                        .padding(.horizontal)
                        
                        
                        Text("Zvanje: \(game.getYouCallsSum())")
                            .font(.subheadline)
                    }
                    .listRowSeparator(.hidden)
                    .padding()
                    
                    Picker("", selection: $game.caller) {
                        ForEach(Caller.allCases, id: \.self) { caller in
                            Text(caller.description)
                        }
                    }
                    .pickerStyle(.segmented)
                    .padding(.horizontal)
                    .padding()
                    
                    renderButtonRow(call: 20)
                    renderButtonRow(call: 50)
                    renderButtonRow(call: 100)
                    renderButtonRow(call: 1001)
                }
            }
            .navigationTitle("Unos")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        //TODO: save game
                        dismiss()
                    } label: {
                        Text("Spremi")
                            .foregroundColor(.primary)
                    }
                }
                ToolbarItem(placement: .navigationBarLeading) {
                    Button {
                        //TODO: save game
                        dismiss()
                    } label: {
                        Text("Odustani")
                            .foregroundColor(.primary)
                    }
                }
            }
        }
    }
    
    private func renderButtonRow(call amount: Int) -> some View {
        HStack {
            Spacer()
            Button {
                handleWeCallUpdate(amount: amount)
            } label: {
                Text(amount == 1001 ? "Belot" : String(amount))
            }
            .roundedAccentButton()
            Spacer()
            Button {
                handleYouCallUpdate(amount: amount)
            } label: {
                Text(amount == 1001 ? "Belot" : String(amount))
            }
            .roundedAccentButton()
            Spacer()
        }
        .listRowSeparator(.hidden)
    }
    
    private func handleWeCallUpdate(amount: Int) {
        if game.getWeCallsSum() < 1001 {
            switch amount {
            case 20:
                game.weCall20 += 1
            case 50:
                game.weCall50 += 1
            case 100:
                game.weCall100 += 1
            case 1001:
                if game.weCallBelot == 0 { game.weCallBelot += 1 }
            default:
                break
            }
        }
    }
    
    private func handleYouCallUpdate(amount: Int) {
        if game.getYouCallsSum() < 1001 {
            switch amount {
            case 20:
                game.youCall20 += 1
            case 50:
                game.youCall50 += 1
            case 100:
                game.youCall100 += 1
            case 1001:
                if game.youCallBelot == 0 { game.youCallBelot += 1 }
            default:
                break
            }
        }
    }
    
    private func resetCalls() {
        game.weCall20 = 0
        game.weCall50 = 0
        game.weCall100 = 0
        game.weCallBelot = 0
        game.youCall20 = 0
        game.youCall50 = 0
        game.youCall100 = 0
        game.youCallBelot = 0
    }
    
}

struct InputView_Previews: PreviewProvider {
    static var previews: some View {
        InputView()
    }
}
