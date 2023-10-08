import Combine
import SwiftUI

struct InputView: View {
    @Environment(\.dismiss) private var dismiss
    
    @StateObject private var viewModel: InputViewModel
    
    @State private var ignoreFlag: Bool = false
    
    init(viewModel: InputViewModel) {
        self._viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        NavigationView {
            GeometryReader { geometryProxy in
                ZStack {
                    Color.green.opacity(0.7).ignoresSafeArea(.all)
                    
                    List {
                        HStack {
                            Spacer()
                            UnderlinedTextField(score: $viewModel.currentGameEdit.weBaseScore)
                                .onChange(of: viewModel.currentGameEdit.weBaseScore) { newValue in
                                    if !ignoreFlag {
                                        ignoreFlag = true
                                        viewModel.onChangeOfWeScore()
                                        ignoreFlag = false
                                    }
                                }
                            Spacer()
                            UnderlinedTextField(score: $viewModel.currentGameEdit.youBaseScore)
                                .onChange(of: viewModel.currentGameEdit.youBaseScore) { newValue in
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
                            .roundedAccentButton(width: geometryProxy.size.width/3.5, height: geometryProxy.size.height/20)
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
                        
                        
                        render20Row(geometryProxy: geometryProxy)
                        render50Row(geometryProxy: geometryProxy)
                        render100Row(geometryProxy: geometryProxy)
                        renderBelotRow(geometryProxy: geometryProxy)
                    }
                    .scrollDismissesKeyboard(.immediately)
                    .scrollContentBackground(.hidden)
                }
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
                    .disabled(viewModel.currentGameEdit.weBaseScore + viewModel.currentGameEdit.youBaseScore  != 162)
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
    
    private func render20Row(geometryProxy: GeometryProxy) -> some View {
        HStack {
            HStack {
                Button {
                    viewModel.currentGameEdit.weCall20 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.weCall20 <= 0)
                .opacity(viewModel.currentGameEdit.weCall20 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.weCall20)
                .padding()
                
                Button {
                    viewModel.handleWeCallUpdate(amount: 20)
                } label: {
                    Text("20")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
            }

            Spacer()
            
            HStack {
                Button {
                    viewModel.handleYouCallUpdate(amount: 20)
                } label: {
                    Text("20")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)

                Button {
                    viewModel.currentGameEdit.youCall20 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.youCall20 <= 0)
                .opacity(viewModel.currentGameEdit.youCall20 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.youCall20)
                .padding()
            }
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
    
    private func render50Row(geometryProxy: GeometryProxy) -> some View {
        HStack {
            HStack {
                Button {
                    viewModel.currentGameEdit.weCall50 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.weCall50 <= 0)
                .opacity(viewModel.currentGameEdit.weCall50 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.weCall50)
                .padding()

                Button {
                    viewModel.handleWeCallUpdate(amount: 50)
                } label: {
                    Text("50")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
            }

            Spacer()
            
            HStack {
                Button {
                    viewModel.handleYouCallUpdate(amount: 50)
                } label: {
                    Text("50")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)

                Button {
                    viewModel.currentGameEdit.youCall50 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.youCall50 <= 0)
                .opacity(viewModel.currentGameEdit.youCall50 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.youCall50)
                .padding()
            }
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
    
    private func render100Row(geometryProxy: GeometryProxy) -> some View {
        HStack {
            HStack {
                Button {
                    viewModel.currentGameEdit.weCall100 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.weCall100 <= 0)
                .opacity(viewModel.currentGameEdit.weCall100 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.weCall100)
                .padding()

                Button {
                    viewModel.handleWeCallUpdate(amount: 100)
                } label: {
                    Text("100")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
            }
            
            Spacer()
            
            HStack {
                Button {
                    viewModel.handleYouCallUpdate(amount: 100)
                } label: {
                    Text("100")
                        .font(.system(size: geometryProxy.size.width/16))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
                
                Button {
                    viewModel.currentGameEdit.youCall100 -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.youCall100 <= 0)
                .opacity(viewModel.currentGameEdit.youCall100 > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.youCall100)
                .padding()
            }
        }
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
    
    private func renderBelotRow(geometryProxy: GeometryProxy) -> some View {
        HStack {
            HStack {
                Button {
                    viewModel.currentGameEdit.weCallBelot -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.weCallBelot <= 0)
                .opacity(viewModel.currentGameEdit.weCallBelot > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.weCallBelot)
                .padding()

                Button {
                    viewModel.handleWeCallUpdate(amount: 1001)
                } label: {
                    Text("Belot")
                        .font(.system(size: geometryProxy.size.width/18))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
            }
            
            Spacer()
            
            HStack {
                Button {
                    viewModel.handleYouCallUpdate(amount: 1001)
                } label: {
                    Text("Belot")
                        .font(.system(size: geometryProxy.size.width/18))
                }
                .roundedAccentButton(width: geometryProxy.size.width/5, height: geometryProxy.size.height/16)
                .buttonStyle(.plain)
                
                Button {
                    viewModel.currentGameEdit.youCallBelot -= 1
                } label: {
                    Image(systemName: "minus.circle")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.secondary)
                        .frame(width: geometryProxy.size.width/15, height: geometryProxy.size.width/15)
                }
                .buttonStyle(.plain)
                .disabled(viewModel.currentGameEdit.youCallBelot <= 0)
                .opacity(viewModel.currentGameEdit.youCallBelot > 0 ? 1 : 0)
                .animation(.bouncy, value: viewModel.currentGameEdit.youCallBelot)
                .padding()
            }
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

