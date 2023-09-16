import SwiftUI

struct MainView: View {
    @StateObject private var viewModel: MainViewModel
    @State private var showInputSheet: Bool
    
    init() {
        _viewModel = .init(wrappedValue: MainViewModel())
        showInputSheet = false
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea()
                
                List {
                    ResultRow(weScore: "MI", youScore: "VI")
                        .bold(true)
                        .padding(.vertical)
                        .padding(.vertical)
                    
                    ForEach(viewModel.currentGame, id: \.self) { game in
                        ResultRow(weScore: game.weScoreString, youScore: game.youScoreString)
                    }
                }
                .scrollContentBackground(.hidden)
            }
            .onAppear { viewModel.onAppear() }
            .sheet(isPresented: $showInputSheet, onDismiss: {
                viewModel.onAppear()
            }, content: {
                InputView(viewModel: InputViewModel())
                    .interactiveDismissDisabled(true)
            })
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        //TODO: Show history
                    } label: {
                        Image(systemName: "clock.arrow.circlepath")
                            .foregroundColor(.primary)
                    }
                }
                
                ToolbarItem(placement: .bottomBar) {
                    HStack {
                        Button {
                            //TODO: Implement counter
                        } label: {
                            Text("Dijeli: Protivnik")
                                .font(.body)
                                .foregroundColor(.primary)
                        }
                        Spacer()
                        Button {
                            showInputSheet = true
                        } label: {
                            Image(systemName: "square.and.pencil")
                                .imageScale(.large)
                                .foregroundColor(.primary)
                        }
                    }
                }
            }
            .navigationTitle("Bela Blok")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
