import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) private var dismiss

    @StateObject private var appState: AppState = .shared
    @StateObject private var viewModel: InputViewModel
    @State private var ignoreFlag: Bool = false
    @State private var weTotal: Int = 0
    @State private var youTotal: Int = 0
    @State private var dealer: Dealer?

    init(viewModel: InputViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }

    var body: some View {
        NavigationStack {
            ZStack {
                Color(.defaultBackground)
                    .ignoresSafeArea()

                List {
                    GlassEffectContainer(spacing: 16) {
                        HStack(spacing: 16) {
                            scoreField(
                                caller: .we,
                                score: $viewModel.currentGameEdit.weBaseScore,
                                callsSum: viewModel.currentGameEdit.weCallsSum,
                                onChange: viewModel.onChangeOfWeScore
                            )
                            scoreField(
                                caller: .you,
                                score: $viewModel.currentGameEdit.youBaseScore,
                                callsSum: viewModel.currentGameEdit.youCallsSum,
                                onChange: viewModel.onChangeOfYouScore
                            )
                        }
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .foregroundStyle(viewModel.currentGameEdit.isStigljaActive ? Color.secondary : Color.primary)
                    .disabled(viewModel.currentGameEdit.isStigljaActive)

                    Picker("", selection: $viewModel.currentGameEdit.caller) {
                        ForEach(Caller.allCases.filter { $0 != .none }, id: \.self) { caller in
                            Text(caller.description)
                        }
                    }
                    .listRowBackground(Color.clear)
                    .pickerStyle(.segmented)
                    .padding(.horizontal)
                    .padding()

                    Group {
                        if appState.selectedCalls.contains(.dvadeset) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCall20,
                                youCall: $viewModel.currentGameEdit.youCall20,
                                amount: 20
                            )
                        }

                        if appState.selectedCalls.contains(.pedeset) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCall50,
                                youCall: $viewModel.currentGameEdit.youCall50,
                                amount: 50
                            )
                        }

                        if appState.selectedCalls.contains(.sto) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCall100,
                                youCall: $viewModel.currentGameEdit.youCall100,
                                amount: 100
                            )
                        }

                        if appState.selectedCalls.contains(.stopedeset) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCall150,
                                youCall: $viewModel.currentGameEdit.youCall150,
                                amount: 150
                            )
                        }

                        if appState.selectedCalls.contains(.dvjesto) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCall200,
                                youCall: $viewModel.currentGameEdit.youCall200,
                                amount: 200
                            )
                        }

                        if appState.selectedCalls.contains(.belot) {
                            renderCallButtonRow(
                                weCall: $viewModel.currentGameEdit.weCallBelot,
                                youCall: $viewModel.currentGameEdit.youCallBelot,
                                amount: 1001
                            )
                        }

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
                .background(Color.clear)
            }
            .navigationTitle("Unos")
            .navigationBarTitleDisplayMode(.inline)
            .onAppear {
                let currentSession = appState.currentGame.forDisplay
                weTotal = currentSession.weTotalAccumulated
                youTotal = currentSession.youTotalAccumulated
                dealer = viewModel.isEditing ? viewModel.currentGameEdit.dealer : appState.currentDealer
            }
            .toolbar {
                ToolbarItem(placement: .principal) {
                    VStack(spacing: 0) {
                        Text("Mi \(weTotal) : \(youTotal) Vi")
                            .font(.headline)
                            .monospacedDigit()
                        if let dealer {
                            Text("Dijeli: \(dealer.description)")
                                .font(.caption)
                                .foregroundStyle(.secondary)
                        }
                    }
                    .accessibilityElement(children: .combine)
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button(role: .confirm) {
                        viewModel.saveCurrentGame()
                        dismiss()
                    }
                    .disabled(
                        (viewModel.currentGameEdit.weBaseScore + viewModel.currentGameEdit.youBaseScore != 162)
                            || viewModel.currentGameEdit.caller == .none
                    )
                }
                ToolbarItem(placement: .cancellationAction) {
                    Button(role: .cancel) {
                        dismiss()
                    }
                }
            }
        }
    }

    private func scoreField(caller: Caller, score: Binding<Int>, callsSum: Int, onChange: @escaping () -> Void) -> some View {
        VStack(spacing: 6) {
            Text(caller.name)
                .font(.subheadline.weight(.semibold))
                .foregroundStyle(.secondary)

            UnderlinedTextField(score: score)
                .onChange(of: score.wrappedValue) {
                    if !ignoreFlag {
                        ignoreFlag = true
                        onChange()
                        ignoreFlag = false
                    }
                }

            HStack {
                Text("Zvanje:")
                    .font(.subheadline)
                Text("\(callsSum)")
                    .font(.subheadline)
                    .contentTransition(.numericText(value: Double(callsSum)))
                    .animation(.snappy, value: callsSum)
            }
        }
        .padding(.vertical, 16)
        .frame(maxWidth: .infinity)
        .glassEffect(.regular, in: .rect(cornerRadius: 28))
    }

    private func renderCallButtonRow(weCall: Binding<Int>, youCall: Binding<Int>, amount: Int) -> some View {
        GlassEffectContainer(spacing: 12) {
            HStack(spacing: 12) {
                HStack(spacing: 8) {
                    callAdjuster(bindedInt: weCall, minusLeading: true)
                    Spacer(minLength: 0)
                    renderCallButton(caller: .we, amount: amount)
                }
                .frame(maxWidth: .infinity)

                HStack(spacing: 8) {
                    renderCallButton(caller: .you, amount: amount)
                    Spacer(minLength: 0)
                    callAdjuster(bindedInt: youCall, minusLeading: false)
                }
                .frame(maxWidth: .infinity)
            }
        }
        .padding(.horizontal)
        .padding(.vertical, 8)
        .animation(.bouncy, value: weCall.wrappedValue > 0)
        .animation(.bouncy, value: youCall.wrappedValue > 0)
    }

    /// Minus button and counter, shown only once the call is pressed.
    /// A hidden copy keeps their space reserved so the row never shifts.
    private func callAdjuster(bindedInt: Binding<Int>, minusLeading: Bool) -> some View {
        adjusterContent(minus: minusButton(bindedInt: .constant(1)), counter: Text("x0").monospaced().fixedSize(), minusLeading: minusLeading)
            .hidden()
            .overlay(alignment: minusLeading ? .leading : .trailing) {
                if bindedInt.wrappedValue > 0 {
                    adjusterContent(minus: minusButton(bindedInt: bindedInt), counter: counter(bindedInt: bindedInt), minusLeading: minusLeading)
                        .transition(.scale.combined(with: .opacity))
                }
            }
    }

    private func adjusterContent(minus: some View, counter: some View, minusLeading: Bool) -> some View {
        HStack(spacing: 4) {
            if minusLeading {
                minus
                counter
            } else {
                counter
                minus
            }
        }
    }

    private func minusButton(bindedInt: Binding<Int>) -> some View {
        Button {
            bindedInt.wrappedValue = max(0, bindedInt.wrappedValue - 1)
        } label: {
            Image(systemName: "minus")
                .frame(width: 16, height: 16)
        }
        .buttonStyle(.glass)
        .buttonBorderShape(.circle)
    }

    private func counter(bindedInt: Binding<Int>) -> some View {
        Text("x\(bindedInt.wrappedValue)")
            .monospaced()
            .fixedSize()
            .contentTransition(.numericText(value: Double(bindedInt.wrappedValue)))
            .animation(.snappy, value: bindedInt.wrappedValue)
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
                .lineLimit(1)
                .minimumScaleFactor(0.7)
                .frame(minWidth: 64)
        }
        .buttonStyle(.glass)
        .controlSize(.large)
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
