import SwiftUI

struct MainView: View {
    @State private var showInputSheet: Bool = false
    var tempData = ["1","2","3"]
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.green.opacity(0.8).ignoresSafeArea()
                
                List {
                    ResultRow(weScore: "MI", youScore: "VI")
                        .bold(true)
                        .padding(.vertical)
                        .padding(.vertical)
                    
                    ForEach(tempData, id: \.self) { _ in
                        ResultRow(weScore: "36", youScore: "126")
                    }
                }
                .scrollContentBackground(.hidden)
            }
            .sheet(isPresented: $showInputSheet, content: {
                InputView()
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
