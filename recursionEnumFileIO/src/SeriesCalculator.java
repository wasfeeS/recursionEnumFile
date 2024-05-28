import java.io.*;
import java.util.Scanner;


public class SeriesCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileWriter writer = null;
        try {
            writer = new FileWriter("series_sum_results.txt", true);
            int choice;
            do {
                System.out.println("Welcome to Series Calculator!\n\nChoose a Mathematical series, or access the files: ");
                System.out.println("1. Arithmetic");
                System.out.println("2. Geometric");
                System.out.println("3. Fibonacci");
                System.out.println("4. Power");
                System.out.println("5. Output Data from File");
                System.out.println("6. Clear Data from File");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        handleSeriesCalculation(choice, scanner, writer);
                        break;
                    case 5:
                        outputData();
                        break;
                    case 6:
                        clearData();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 7.");
                }
            } while (choice != 0);
            System.out.println("Thank you for using Series Calculator!.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handleSeriesCalculation(int choice, Scanner scanner, FileWriter writer) throws IOException {
        SeriesCategory category = SeriesCategory.values()[choice - 1];
        int start, base, terms;
        if (category != SeriesCategory.FIBONACCI && category != SeriesCategory.POWER) {
            System.out.print("Enter starting term: ");
            start = scanner.nextInt();
            System.out.print("Enter difference/ratio: ");
            base = scanner.nextInt();
        } else {
            start = 1; 
            base = 0; 
        }
        if (category == SeriesCategory.POWER) {
            System.out.print("Enter the starting power: ");
            start = scanner.nextInt();
            System.out.print("Enter the base: ");
            base = scanner.nextInt();
        }
        System.out.print("Enter the number of terms: ");
        terms = scanner.nextInt();
        calculateAndStore(category, start, base, terms, writer);
    }

    private static void calculateAndStore(SeriesCategory category, int start, int base, int terms, FileWriter writer) throws IOException {
        String seriesType = "";
        StringBuilder seriesTerms = new StringBuilder();
        double sum = 0;
        switch (category) {
            case ARITHMETIC:
                seriesType = "Arithmetic";
                sum = arithmetic(start, base, terms, seriesTerms);
                break;
            case GEOMETRIC:
                seriesType = "Geometric";
                sum = geometric(start, base, terms, seriesTerms);
                break;
            case FIBONACCI:
                seriesType = "Fibonacci";
                sum = fibonacci(terms, seriesTerms);
                break;
            case POWER:
                seriesType = "Power";
                sum = power(start, base, terms, seriesTerms);
                break;
        }
        System.out.println(seriesType + " Series:");
        System.out.println("Terms: " + seriesTerms.toString());
        System.out.println("Sum: " + sum);
        writer.write(seriesType + " Series (Start: " + start + ", Base: " + base + ", Terms: " + terms + "): Sum = " + sum + "\n");
        writer.write("Terms: " + seriesTerms.toString() + "\n\n");
    }

    private static double arithmetic(int start, int difference, int terms, StringBuilder seriesTerms) {
        double sum = 0;
        for (int i = 0; i < terms; i++) {
            int term = start + (i * difference);
            seriesTerms.append(term);
            sum += term;
            if (i < terms - 1) {
                seriesTerms.append(", ");
            }
        }
        return sum;
    }

    private static double geometric(int start, int ratio, int terms, StringBuilder seriesTerms) {
        double sum = 0;
        for (int i = 0; i < terms; i++) {
            int term = (int) (start * Math.pow(ratio, i));
            seriesTerms.append(term);
            sum += term;
            if (i < terms - 1) {
                seriesTerms.append(", ");
            }
        }
        return sum;
    }

    private static double fibonacci(int n, StringBuilder seriesTerms) {
        double total = 0;
        int fib1 = 0, fib2 = 1;
        for (int i = 0; i < n; i++) {
            seriesTerms.append(fib1);
            total += fib1;
            if (i < n - 1) {
                seriesTerms.append(", ");
            }
            int next = fib1 + fib2;
            fib1 = fib2;
            fib2 = next;
        }
        return total;
    }

    private static double power(int start, int base, int terms, StringBuilder seriesTerms) {
        double total = 0;
        for (int i = start; i < start + terms; i++) {
            double term = Math.pow(base, i);
            seriesTerms.append(term);
            total += term;
            if (i < start + terms - 1) {
                seriesTerms.append(", ");
            }
        }
        return total;
    }

    private static void outputData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("series_sum_results.txt"))) {
            String line;
            System.out.println("Data from file:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static void clearData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("series_sum_results.txt"))) {
            System.out.println("Data cleared.");
        } catch (IOException e) {
            System.out.println("An error.");
            e.printStackTrace();
        }
    }
}