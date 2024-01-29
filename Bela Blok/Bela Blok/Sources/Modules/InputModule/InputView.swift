import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) private var dismiss

    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: InputViewModel
    @State private var ignoreFlag: Bool = false

    init(viewModel: InputViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }

    var body: some View {
        NavigationView {
            ZStack {
                Color(.defaultBackground)
                    .ignoresSafeArea()

                List {
                    HStack {
                        Spacer()
                        UnderlinedTextField(score: $viewModel.currentGameEdit.weBaseScore)
                            .onChange(of: viewModel.currentGameEdit.weBaseScore) { _ in
                                if !ignoreFlag {
                                    ignoreFlag = true
                                    viewModel.onChangeOfWeScore()
                                    ignoreFlag = false
                                }
                            }
                        Spacer()
                        UnderlinedTextField(score: $viewModel.currentGameEdit.youBaseScore)
                            .onChange(of: viewModel.currentGameEdit.youBaseScore) { _ in
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
                    .foregroundStyle(viewModel.currentGameEdit.isStigljaActive ? Color.gray : Color.primary)
                    .disabled(viewModel.currentGameEdit.isStigljaActive)
                    .padding()

                    HStack {
                        Spacer()
                        VStack {
                            Text("Zvanje")
                                .font(.subheadline)
                            Text("\(viewModel.currentGameEdit.weCallsSum)")
                                .font(.subheadline)
                        }
                        Spacer()

                        Button {
                            viewModel.resetCalls()
                        } label: {
                            Text("Obriši zvanja")
                                .font(.subheadline)
                        }
                        .roundedAccentButton(width: 110, height: 35)
                        .buttonStyle(.plain)

                        .padding(.horizontal)
                        Spacer()

                        VStack {
                            Text("Zvanje")
                                .font(.subheadline)
                            Text("\(viewModel.currentGameEdit.youCallsSum)")
                                .font(.subheadline)
                        }
                        Spacer()
                    }
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
                    .padding()

                    Picker("", selection: $viewModel.currentGameEdit.caller) {
                        ForEach(Caller.allCases, id: \.self) { caller in
                            Text(caller.description)
                        }
                    }
                    .listRowBackground(Color.clear)
                    .pickerStyle(.segmented)
                    .padding(.horizontal)
                    .padding()

                    Group {
                        renderCallButtonRow(
                            weCall: $viewModel.currentGameEdit.weCall20,
                            youCall: $viewModel.currentGameEdit.youCall20,
                            amount: 20
                        )

                        renderCallButtonRow(
                            weCall: $viewModel.currentGameEdit.weCall50,
                            youCall: $viewModel.currentGameEdit.youCall50,
                            amount: 50
                        )

                        renderCallButtonRow(
                            weCall: $viewModel.currentGameEdit.weCall100,
                            youCall: $viewModel.currentGameEdit.youCall100,
                            amount: 100
                        )

                        renderCallButtonRow(
                            weCall: $viewModel.currentGameEdit.weCallBelot,
                            youCall: $viewModel.currentGameEdit.youCallBelot,
                            amount: 1001
                        )

                        renderCallButtonRow(
                            weCall: $viewModel.currentGameEdit.weStiglja,
                            youCall: $viewModel.currentGameEdit.youStiglja,
                            amount: 90
                        )
                    }
                    .listRowInsets(EdgeInsets())
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
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
                    }
                    .disabled(viewModel.currentGameEdit.weBaseScore + viewModel.currentGameEdit.youBaseScore != 162)
                }
                ToolbarItem(placement: .cancellationAction) {
                    Button {
                        dismiss()
                    } label: {
                        Text("Odustani")
                    }
                }
            }
        }
    }

    private func renderCallButtonRow(weCall: Binding<Int>, youCall: Binding<Int>, amount: Int) -> some View {
        HStack {
            VStack {
                HStack {
                    minusButton(bindedInt: weCall)
                    counter(bindedInt: weCall)
                    Spacer()
                    renderCallButton(caller: .we, amount: amount)
                    Spacer()
                }
            }

            VStack {
                HStack {
                    Spacer()
                    renderCallButton(caller: .you, amount: amount)
                    Spacer()
                    counter(bindedInt: youCall)
                    minusButton(bindedInt: youCall)
                }
            }
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical)
    }

    private func minusButton(bindedInt: Binding<Int>) -> some View {
        Button {
            bindedInt.wrappedValue -= 1
        } label: {
            Image(systemName: "minus.circle")
                .resizable()
                .scaledToFit()
                .frame(width: 28, height: 28)
        }
        .buttonStyle(.plain)
        .disabled(bindedInt.wrappedValue <= 0)
        .opacity(bindedInt.wrappedValue > 0 ? 1 : 0)
        .animation(.bouncy, value: bindedInt.wrappedValue)
    }

    private func counter(bindedInt: Binding<Int>) -> some View {
        Text("x\(bindedInt.wrappedValue)")
            .monospaced()
            .opacity(bindedInt.wrappedValue == 0 ? 0 : 1)
    }

    private func renderCallButton(caller: Caller, amount: Int) -> some View {
        Button {
            if caller == .we {
                viewModel.handleWeCallUpdate(amount: amount)
            } else {
                viewModel.handleYouCallUpdate(amount: amount)
            }
        } label: {
            Text(handleButtonLabel(amount: amount))
                .font(.system(size: 24))
        }
        .roundedAccentButton(width: 80, height: 44)
        .buttonStyle(.plain)
    }

    private func handleButtonLabel(amount: Int) -> String {
        switch amount {
        case 1001:
            return "Belot"
        case 90:
            return "Štiglja"
        default:
            return String(amount)
        }
    }
}

struct InputView_Previews: PreviewProvider {
    static var previews: some View {
        InputView(viewModel: InputViewModel())
    }
}
