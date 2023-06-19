import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Reclame extends Task {

    static Integer N;
    static Integer M;
    static Integer K;
    static Integer OK = 0;
    List<Integer> firstList = new ArrayList<>();
    List<Integer> secondList = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Reclame reclame = new Reclame();
        reclame.readProblemData();
        reclame.formulateOracleQuestion();
    }

    @Override
    public void solve() throws IOException, InterruptedException {

    }

    @Override
    public void readProblemData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String buffer = bufferedReader.readLine();
        read_Data(buffer);
        int i = 0;
        while (i < M) {
            buffer = bufferedReader.readLine();
            get_st_nd(buffer);
            i++;
        }
    }

    private void get_st_nd(String buffer) {
        int k = 0;
        int st = 0;
        while (true) {
            if (buffer.charAt(k) == ' ') break;
            st = st * 10 + buffer.charAt(k) - '0';
            k++;
        }
        k++;
        int nd = 0;
        while (true) {
            if (k >= buffer.length()) break;
            nd = nd * 10 + buffer.charAt(k) - '0';
            k++;
        }
        firstList.add(st);
        secondList.add(nd);
    }

    private void read_Data(String buffer) {
        int k = 0;
        N = 0;
        while (true) {
            if (buffer.charAt(k) == ' ') break;
            N = N * 10 + buffer.charAt(k) - '0';
            k++;
        }
        k++;
        M = 0;
        while (true) {
            if (k >= buffer.length()) break;
            M = M * 10 + buffer.charAt(k) - '0';
            k++;
        }
    }

    @Override
    public void formulateOracleQuestion() throws IOException, InterruptedException {
        K = 2;
        while (K <= N) {
            FileWriter outputFile = new FileWriter("sat.cnf");
            int v = K + M + K*(K-1)*N/2;
            v = v + K * N * (N-1) / 2;
            outputFile.write("p cnf " + K*N + " " + v + '\n');
            {
                int i = 0;
                while (i < K) {
                    int j = 1;
                    while (j <= N) {
                        outputFile.write(i * N + j + " ");
                        j++;
                    }
                    outputFile.write("0\n");
                    i++;
                }
            }
            {
                int i = 0;
                while (i < K - 1) {
                    int j = i + 1;
                    while (j <= K - 1) {
                        int a = 1;
                        while (a <= N) {
                            int x = -(a + i * N);
                            int y = -(a + j * N);
                            outputFile.write(x + " " + y + " 0\n");
                            a++;
                        }
                        j++;
                    }
                    i++;
                }
            }

            int k = 0;
            while (k < K) {
                int i = 1;
                while (i < N) {
                    int j = i + 1;
                    while (j <= N) {
                        int x = -(i + k * N);
                        int y = -(j + k * N);
                        outputFile.write(x + " " + y + " 0\n");
                        j++;
                    }
                    i++;
                }
                k++;
            }

            int i = 0;
            while (i < M) {
                int j = 0;
                while (j < K) {
                    int w1 = firstList.get(i) + j * N;
                    int w2 = secondList.get(i) + j * N;
                    outputFile.write(w1 + " " + w2 + " ");
                    j++;
                }
                outputFile.write("0\n");
                i++;
            }
            outputFile.close();
            askOracle();
            decipherOracleAnswer();
            if (OK == 1) {
                break;
            }
            K++;
        }
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("sat.sol"));
        String buffer = reader.readLine();
        if (!buffer.contains("True")) {
            return;
        }
        buffer = reader.readLine();
        int v = 0, k = 0;
        if (k < buffer.length()) {
            do {
                v = v * 10 + buffer.charAt(k) - '0';
                k++;
            } while (k < buffer.length());
        }
        List<Integer> list = new ArrayList();
        buffer = reader.readLine();
        k = 0;
        for (int i = 0; i < v; i++) {
            int nr = 0;
            int sign = 1;
            while (buffer.charAt(k) != ' ') {
                if (buffer.charAt(k) == '-') {
                    sign = -1;
                } else {

                    nr = nr * 10 + buffer.charAt(k) - '0';
                }
                k++;
            }
            k++;
            if (sign == 1)
                list.add(nr);
        }
        for (Integer i: list) {
            int k2 = i % N;
            if (k2 == 0)
                k2 = N;
            System.out.print(k2 + " ");
        }
        OK = 1;
    }

    @Override
    public void writeAnswer() throws IOException {

    }
}