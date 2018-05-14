package space.janiekitty.data.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.janiekitty.data.model.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    private Data data;

    public DataService(Data data) {
        this.data = data;
    }

    public Data execute() {
        data.setError(badData(data.getRawData()));
        if (!data.isError()) {
            data.setDataPoints(processDataPoints(data.getRawData()));
            data.setError(notEnoughPoints(data.getDataPoints()));
            if (!data.isError()) {
                data.setProcessedData(processedString(data.getDataPoints()));
                data.setMin(calculateMin(data.getDataPoints()));
                data.setMax(calculateMax(data.getDataPoints()));
                data.setRange(calculateRange(data.getDataPoints()));
                data.setMean(calculateMean(data.getDataPoints()));
                data.setMedian(calculateMedian(data.getDataPoints()));
                data.setMode(calculateMode(data.getDataPoints()));
                data.setLowerQuartile1(calculateLowerQuartile1(data.getDataPoints()));
                data.setUpperQuartile1(calculateUpperQuartile1(data.getDataPoints()));
                data.setOutliers(calculateOutliers(data.getDataPoints()));
            } else {
                data.setErrorMessage("Your data must contain at least 3 data points.");
            }
        } else {
            data.setErrorMessage("Your data has at least one bad value.");
        }
        return this.data;
    }

    private boolean badData(String values) {
        return Stream.of(values.split("[\\s,;]+")).anyMatch(number -> !StringUtils.isNumeric(number));
    }

    private boolean notEnoughPoints(List<BigDecimal> values) {
        return values.size() < 3;
    }

    private List<BigDecimal> processDataPoints(String values) {
        return Stream.of(values.split("[\\s,;]+"))
                .map(elem -> new BigDecimal(elem))
                .sorted()
                .collect(Collectors.toList());
    }

    private String processedString(List<BigDecimal> values) {
        return values.stream().map(BigDecimal::toPlainString).collect(Collectors.joining(","));
    }

    private BigDecimal calculateMin(List<BigDecimal> values) {
        return values.get(0);
    }

    private BigDecimal calculateMax(List<BigDecimal> values) {
        return values.get(values.size() - 1);
    }

    private BigDecimal calculateRange(List<BigDecimal> values) {
        return values.get(values.size() - 1).subtract(values.get(0));
    }

    private BigDecimal calculateMean(List<BigDecimal> values) {
        BigDecimal sum =
                values.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(this.data.getDataPoints().size()), MathContext.DECIMAL32);
    }

    public static BigDecimal calculateMedian(List<BigDecimal> values) {
        int middle = values.size() / 2;
        if (values.size() % 2 == 1) {
            return values.get(middle);
        } else {
            return (values
                    .get(middle - 1)
                    .add(values.get(middle))
                    .divide(new BigDecimal("2"), MathContext.DECIMAL32));
        }
    }

    public static List<BigDecimal> calculateMode(List<BigDecimal> values) {
        final Map<BigDecimal, Long> countFrequencies =
                values.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final long maxFrequency =
                countFrequencies.values().stream().mapToLong(count -> count).max().orElse(-1);

        return countFrequencies
                .entrySet()
                .stream()
                .filter(potentialMode -> potentialMode.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }

    public static BigDecimal calculateLowerQuartile1(List<BigDecimal> values) {
        int middle = values.size() / 2;
        return calculateMedian(values.subList(0, middle));
    }

    public static BigDecimal calculateUpperQuartile1(List<BigDecimal> values) {
        int middle = values.size() / 2;
        if (values.size() % 2 == 1) {
            middle = middle + 1;
        }
//          logger.info("The list of values is " + values.subList(middle, values.size()).toString());
        return calculateMedian(values.subList(middle, values.size()));
    }

    public static List<BigDecimal> calculateOutliers(List<BigDecimal> values) {
        //        final Map<BigDecimal, Long> countFrequencies = values.stream()
        //                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //
        //        final long maxFrequency = countFrequencies.values().stream()
        //                .mapToLong(count -> count)
        //                .max().orElse(-1);
        //
        //        return countFrequencies.entrySet().stream()
        //                .filter(potentialMode -> potentialMode.getValue() == maxFrequency)
        //                .map(Map.Entry::getKey)
        //                .sorted()
        //                .collect(Collectors.toList());
        return new ArrayList<>();
    }
}
