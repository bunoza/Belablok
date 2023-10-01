import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) private var dismiss
    
    @ObservedObject private var viewModel: InputViewModel
    
    @State private var ignoreFlag: Bool = false
    
    init(viewModel: InputViewModel) {
        self.viewModel = viewModel
    }
    
    //TODO: introduce geometry reader
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea(.all)
                
                List {
                    HStack {
                        Spacer()
                        UnderlinedTextField(score: $viewModel.currentGame.weBaseScore)
                            .onChange(of: viewModel.currentGame.weBaseScore) { newValue in
                                if !ignoreFlag {
                                    ignoreFlag = true
                                    viewModel.onChangeOfWeScore()
                                    ignoreFlag = false
                                }
                            }
                        Spacer()
                        UnderlinedTextField(score: $viewModel.currentGame.youBaseScore)
                            .onChange(of: viewModel.currentGame.youBaseScore) { newValue in
                                if !ignoreFlag {
                                    ignoreFlag = true
                                    viewModel.onChangeOfYouScore()
                                    ignoreFlag = false
                                }
                            }
                        Spacer()
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .padding()
                    
                    HStack {
                        Spacer()
                        VStack {
                            Text("Zvanje")
                                .font(.subheadline)
                            Text("\(viewModel.currentGame.weCallsSum)")
                                .font(.subheadline)
                        }
                        Spacer()
                        
                        Button {
                            viewModel.resetCalls()
                        } label: {
                            Text("Obriši zvanja")
                                .font(.subheadline)
                        }
                        .buttonStyle(.borderedProminent)
                        .padding(.horizontal)
                        Spacer()
                        
                        VStack {
                            Text("Zvanje")
                                .font(.subheadline)
                            Text("\(viewModel.currentGame.youCallsSum)")
                                .font(.subheadline)
                        }
                        Spacer()
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
                .scrollDismissesKeyboard(.immediately)
                .scrollContentBackground(.hidden)
            }
            .navigationTitle("Unos")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .confirmationAction) {
                    Button {
                        viewModel.saveCurrentGame()
                        dismiss()
                    } label: {
                        Text("Spremi")
                            .foregroundColor(.primary)
                    }
                    .disabled(viewModel.currentGame.weBaseScore + viewModel.currentGame.youBaseScore  != 162)
                }
                ToolbarItem(placement: .cancellationAction) {
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

