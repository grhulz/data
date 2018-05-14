package space.janiekitty.data.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Data {
    String rawData;
    List<BigDecimal> dataPoints = new ArrayList<>();
    String processedData;
    boolean error = false;
    String errorMessage = "";
    BigDecimal min;
    BigDecimal max;
    BigDecimal range;
    BigDecimal mean;
    BigDecimal median;
    List<BigDecimal> mode;
    BigDecimal lowerQuartile1;
    BigDecimal upperQuartile1;
    List<BigDecimal> outliers;

    public Data() {
    }

    public Data(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<BigDecimal> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<BigDecimal> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public void setMean(BigDecimal mean) {
        this.mean = mean;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }

    public List<BigDecimal> getMode() {
        return mode;
    }

    public void setMode(List<BigDecimal> mode) {
        this.mode = mode;
    }

    public BigDecimal getLowerQuartile1() {
        return lowerQuartile1;
    }

    public void setLowerQuartile1(BigDecimal lowerQuartile1) {
        this.lowerQuartile1 = lowerQuartile1;
    }

    public BigDecimal getUpperQuartile1() {
        return upperQuartile1;
    }

    public void setUpperQuartile1(BigDecimal upperQuartile1) {
        this.upperQuartile1 = upperQuartile1;
    }

    public List<BigDecimal> getOutliers() {
        return outliers;
    }

    public void setOutliers(List<BigDecimal> outliers) {
        this.outliers = outliers;
    }
}
