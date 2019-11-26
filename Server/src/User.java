import java.io.PrintWriter;

public class User {
    private String name;
    private PrintWriter printWriter;

    public User(String imie, PrintWriter printWriter) {
        this.name = imie;
        this.printWriter = printWriter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    @Override
    public String toString() {
        return name;

    }
}
