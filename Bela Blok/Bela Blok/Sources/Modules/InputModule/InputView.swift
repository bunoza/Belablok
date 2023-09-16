import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) var dismiss
    
    @ObservedObject var viewModel: InputViewModel
    
    init(viewModel: InputViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea(.container)
                
                List {
                    HStack {
                        UnderlinedTextField(text: $viewModel.currentGame.weScoreString)
                            .onChange(of: viewModel.currentGame.weScoreString) { newValue in
                                viewModel.onChangeOfWeScore(weScore: newValue)
                            }
                        
                        UnderlinedTextField(text: $viewModel.currentGame.youScoreString)
                            .onChange(of: viewModel.currentGame.youScoreString) { newValue in
                                viewModel.onChangeOfYouScore(youScore: newValue)
                            }
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .padding()
                    
                    HStack {
                        Text("Zvanje: \(viewModel.currentGame.getWeCallsSum())")
                            .font(.subheadline)
                        
                        
                        Button {
                            viewModel.resetCalls()
                        } label: {
                            Text("Obriši zvanja")
                                .font(.subheadline)
                        }
                        .buttonStyle(.borderedProminent)
                        .padding(.horizontal)
                        
                        
                        Text("Zvanje: \(viewModel.currentGame.getYouCallsSum())")
                            .font(.subheadline)
                    }
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
                    .padding()
                    
                    Picker("", selection: $viewModel.currentGame.caller) {
                        ForEach(Caller.allCases, id: \.self) { caller in
                            Text(caller.description)
                        }
                    }
                    .listRowBackground(Color.clear)
                    .pickerStyle(.segmented)
                    .padding(.horizontal)
                    .padding()
                    
                    renderButtonRow(call: 20)
                    renderButtonRow(call: 50)
                    renderButtonRow(call: 100)
                    renderButtonRow(call: 1001)
                }
                .scrollContentBackground(.hidden)
            }
            .navigationTitle("Unos")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        viewModel.saveCurrentGame()
                        dismiss()
                    } label: {
                        Text("Spremi")
                            .foregroundColor(.primary)
                    }
                }
                ToolbarItem(placement: .navigationBarLeading) {
                    Button {
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
                viewModel.handleWeCallUpdate(amount: amount)
            } label: {
                Text(amount == 1001 ? "Belot" : String(amount))
            }
            .roundedAccentButton()
            .buttonStyle(.plain)
            Spacer()
            Button {
                viewModel.handleYouCallUpdate(amount: amount)
            } label: {
                Text(amount == 1001 ? "Belot" : String(amount))
            }
            .roundedAccentButton()
            .buttonStyle(.plain)
            Spacer()
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }    
}

struct InputView_Previews: PreviewProvider {
    static var previews: some View {
        InputView(viewModel: InputViewModel())
    }
}
