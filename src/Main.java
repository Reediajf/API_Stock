import controller.ScannerController;
import model.Menu;
import repository.ProdutoRepository;
import service.ProdutoService;


public class Main {
    public static void main(String[] args) {
        ScannerController scannerController = new ScannerController();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        ProdutoService produtoService = new ProdutoService(produtoRepository, scannerController);
        new Menu(produtoService, scannerController);

    }
}
