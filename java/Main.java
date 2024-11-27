// Not the best practice, but using .* for now.
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.time.*;
import java.util.function.*;
import java.util.Comparator;

/**
 * Demonstrates advanced Java language features.
 */
public class Main {

    // Example Of Text Blocks (Java 13+)
    public static String getMultilineText() {
        return """
                {
                    "name": "Advanced Java Features",
                    "version": "1.0",
                    "description": "A demonstration of modern Java features"
                }
               """;
    }

    // Demonstration Of Sealed Classes (Java 15+)
    sealed interface Shape permits Circle, Rectangle {}

    record Circle(double radius) implements Shape {}

    record Rectangle(double length, double breadth) implements Shape {}

    public static void describeShape(Shape shape) {
        // Pattern Matching For Instanceof (Java 16+)
        if (shape instanceof Circle circle) {
            System.out.println("Circle with radius: " + circle.radius());
        } else if (shape instanceof Rectangle rectangle) {
            System.out.println("Rectangle with length: " + rectangle.length() + " and breadth: " + rectangle.breadth());
        }
    }

    // Example Of Switch Expressions (Java 14+)
    public static String switchExpressionExample(Shape shape) {
        return switch (shape) {
            case Circle c -> "A Circle";
            case Rectangle r -> "A Rectangle";
            default -> "Unknown Shape";
        };
    }

    // Virtual Threads With Project Loom (Java 19+)
    public static void demonstrateVirtualThreads() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        IntStream.range(1, 6).forEach(i -> executor.submit(() -> {
            System.out.println("Running task " + i + " on thread: " + Thread.currentThread());
        }));
        executor.shutdown();
    }

    // Example Of Records (Java 14+)
    public record Person(String name, int age) {}

    public static void demonstrateRecords() {
        Person person = new Person("Alice", 30);
        System.out.println("Person: " + person.name() + ", Age: " + person.age());
    }

    // Example of Stream API Enhancements
    public static void demonstrateStreamAPI() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana");
        Map<Integer, List<String>> groupedByLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        groupedByLength.forEach((length, nameList) -> System.out.println(length + ": " + nameList));
    }

    // Example Of Optional API Enhancements
    public static void demonstrateOptionalAPI() {
        Optional<String> optionalName = Optional.of("Alice");
        String result = optionalName.orElseThrow();
        System.out.println("Optional value: " + result);
    }

    // Generics And Wildcards Example
    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    static class FruitBox extends Box<String> {
        public FruitBox(String value) {
            super(value);
        }
    }

    static class CarBox extends Box<Integer> {
        public CarBox(Integer value) {
            super(value);
        }
    }

    // Wildcards With `extends` 
    public static void printBoxValues(List<? extends Box<?>> boxes) {
        for (Box<?> box : boxes) {
            System.out.println("Box contains: " + box.getValue());
        }
    }

    // Wildcards With `super`
    public static void addToBox(List<? super Integer> list) {
        list.add(100);
        System.out.println("Added to Box: " + list);
    }

    // Example Of CompletableFuture
    public static CompletableFuture<String> asyncTask() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // Simulating delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task Complete";
        });
    }

    // Example of filtering and mapping with streams
    public static void demonstrateStreamOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, null, 4, 5);

        List<Integer> filteredNumbers = numbers.stream()
                .filter(Objects::nonNull) // Filter out null values
                .map(num -> num * 2) // Double each number
                .collect(Collectors.toList());

        System.out.println("Filtered and mapped numbers: " + filteredNumbers);
    }

    // Example of advanced stream operations with HashMap
    public static void demonstrateStreamWithMap() {
        Map<Integer, String> map = Map.of(1, "One", 2, "Two", 3, "Three", 4, "Four");

        Map<Integer, String> filteredMap = map.entrySet().stream()
                .filter(entry -> entry.getKey() % 2 == 0) // Filter entries where key is even
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Filtered map (even keys): " + filteredMap);
    }

    // Advanced Stream operations
    public static void demonstrateAdvancedStreamOperations() {
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        // peek
        numbers.stream().peek(n -> System.out.println("Peek: " + n)).collect(Collectors.toList());

        // skip
        numbers.stream().skip(5).forEach(System.out::println);

        // limit
        numbers.stream().limit(3).forEach(System.out::println);

        // distinct
        numbers.stream().distinct().forEach(System.out::println);

        // sorted
        numbers.stream().sorted().forEach(System.out::println);

        // map
        numbers.stream().map(n -> "Number: " + n).forEach(System.out::println);

        // counting
        long count = numbers.stream().collect(Collectors.counting());
        System.out.println("Count: " + count);

        // reducing
        Optional<Integer> sum = numbers.stream().collect(Collectors.reducing(Integer::sum));
        sum.ifPresent(s -> System.out.println("Sum: " + s));

        // summaryStatistics
        IntSummaryStatistics stats = numbers.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("Summary: " + stats);

        // parallel stream
        numbers.parallelStream().forEach(n -> System.out.println("Parallel: " + n));

        // collectingAndThen
        List<Integer> collected = numbers.stream().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        System.out.println("Collected: " + collected);

        // BinaryOperator.maxBy with comparator
        Optional<Integer> max = numbers.stream().collect(Collectors.maxBy(Comparator.naturalOrder()));
        max.ifPresent(m -> System.out.println("Max: " + m));
    }

    // LinkedHashMap usage for streams
    public static void demonstrateLinkedHashMap() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana");

        LinkedHashMap<Integer, String> map = names.stream()
                .collect(Collectors.toMap(String::length, name -> name, (existing, replacement) -> existing, LinkedHashMap::new));

        System.out.println("LinkedHashMap: " + map);
    }

    // Marker Interface Example
    interface MarkerInterface {}

    static class SomeClass implements MarkerInterface {
        public void display() {
            System.out.println("Marker Interface Implemented.");
        }
    }

    // Demonstrating Date-Time API
    public static void demonstrateDateTimeAPI() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));

        System.out.println("Current Date: " + currentDate);
        System.out.println("Current Time: " + currentTime);
        System.out.println("Current UTC Time: " + zonedDateTime);
    }

    // Interfaces With Static And Default Methods
    interface MyInterface {
        default void defaultMethod() {
            System.out.println("This is a default method in interface");
        }

        static void staticMethod() {
            System.out.println("This is a static method in interface");
        }
    }

    // Function, Predicate, Consumer, Supplier examples
    public static void demonstrateFunctionalInterfaces() {
        // Function: Takes an input and produces an output
        Function<Integer, Integer> square = x -> x * x;
        System.out.println("Square of 5: " + square.apply(5));

        // Predicate: Tests a condition on the input
        Predicate<String> isNotEmpty = str -> !str.isEmpty();
        System.out.println("Is string 'Hello' not empty? " + isNotEmpty.test("Hello"));

        // Consumer: Consumes the input and performs an action
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        printUpperCase.accept("hello");

        // Supplier: Supplies a value (no input)
        Supplier<String> getGreeting = () -> "Hello, World!";
        System.out.println(getGreeting.get());
    }

    public static void main(String[] args) {
        // Demonstrating Text Blocks
        System.out.println("Text Blocks Example:\n" + getMultilineText());

        // Demonstrating Sealed Classes and Pattern Matching
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        describeShape(circle);
        describeShape(rectangle);

        // Demonstrating Switch Expressions
        System.out.println("Switch Expression Example: " + switchExpressionExample(circle));

        // Demonstrating Virtual Threads
        System.out.println("Demonstrating Virtual Threads:");
        demonstrateVirtualThreads();

        // Demonstrating Records
        System.out.println("Demonstrating Records:");
        demonstrateRecords();

        // Demonstrating Stream API 
        System.out.println("Demonstrating Stream API Enhancements:");
        demonstrateStreamAPI();

        // Demonstrating Optional API 
        System.out.println("Demonstrating Optional API Enhancements:");
        demonstrateOptionalAPI();

        // Demonstrating Generics With Wildcards
        List<Box<String>> fruitBoxes = List.of(new FruitBox("Apple"), new FruitBox("Banana"));
        List<Box<Integer>> carBoxes = List.of(new CarBox(123), new CarBox(456));
        System.out.println("Demonstrating Wildcards in Generics:");
        printBoxValues(fruitBoxes);
        printBoxValues(carBoxes);

        // Demonstrating CompletableFuture
        System.out.println("Demonstrating CompletableFuture:");
        asyncTask().thenAccept(System.out::println);

        // Demonstrating Stream Operations With Map
        System.out.println("Demonstrating Stream Operations with Map:");
        demonstrateStreamWithMap();

        // Demonstrating Stream Operations With Filtering And Mapping
        System.out.println("Demonstrating Stream Operations (null filtering and mapping):");
        demonstrateStreamOperations();

        // Demonstrating Advanced Stream Operations
        System.out.println("Demonstrating Advanced Stream Operations:");
        demonstrateAdvancedStreamOperations();

        // Demonstrating LinkedHashMap (streams)
        System.out.println("Demonstrating LinkedHashMap:");
        demonstrateLinkedHashMap();

        // Demonstrating Date-Time API
        System.out.println("Demonstrating Date-Time API:");
        demonstrateDateTimeAPI();

        // Demonstrating Interfaces With Static And Default Methods
        MyInterface.staticMethod();
        MyInterface myInterface = new MyInterface() {}; // Anonymous implementation
        myInterface.defaultMethod();

        // Demonstrating Functional Interfaces
        System.out.println("Demonstrating Functional Interfaces:");
        demonstrateFunctionalInterfaces();
    }
}

