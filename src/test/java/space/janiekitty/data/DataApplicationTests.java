package space.janiekitty.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import space.janiekitty.data.model.Data;
import space.janiekitty.data.service.DataService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataApplicationTests {

    @Test
    public void testDataServiceErrorTrue() {
        Data data = new Data("1 2, 3\n7 a");
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.isError(), is(true));
    }

    @Test
    public void testDataServiceNotEnoughPoints() {
        Data data = new Data("1 2");
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.isError(), is(true));
    }

    @Test
    public void testDataServiceEvenMedian() {
        Data data = new Data("1 2, 3\n7");
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.getMedian(), is(new BigDecimal("2.5")));
    }

    @Test
    public void testDataServiceExecute() {
        Data data = new Data("4,1 1\n2,  7");
        List<BigDecimal> validData =
                new ArrayList<>(
                        Arrays.asList(
                                new BigDecimal("1"),
                                new BigDecimal("1"),
                                new BigDecimal("2"),
                                new BigDecimal("4"),
                                new BigDecimal("7")));
        List<BigDecimal> validMode = new ArrayList<>(Arrays.asList(new BigDecimal("1")));
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.isError(), is(false));
        assertThat(data.getDataPoints().equals(validData), is(true));
        assertThat(data.getProcessedData(), is("1,1,2,4,7"));
        assertThat(data.getMin(), is(new BigDecimal("1")));
        assertThat(data.getMax(), is(new BigDecimal("7")));
        assertThat(data.getRange(), is(new BigDecimal("6")));
        assertThat(data.getMean(), is(new BigDecimal("3")));
        assertThat(data.getMedian(), is(new BigDecimal("2")));
        assertThat(data.getMode().equals(validMode), is(true));
    }

    @Test
    public void testDataServiceMultiMode() {
        Data data = new Data("4,1 1\n2,  7,7");
        List<BigDecimal> validMode =
                new ArrayList<>(Arrays.asList(new BigDecimal("1"), new BigDecimal("7")));
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.getMode().equals(validMode), is(true));
    }

    @Test
    public void testDataServiceNoMode() {
        Data data = new Data("4,1\n2,  7");
        List<BigDecimal> validMode =
                new ArrayList<>(
                        Arrays.asList(
                                new BigDecimal("1"),
                                new BigDecimal("2"),
                                new BigDecimal("4"),
                                new BigDecimal("7")));
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.getMode().equals(validMode), is(true));
    }

    @Test
    public void testDataServiceQuartile1Odd() {
        Data data = new Data("6, 7, 15, 36, 39, 40, 41, 42, 43, 47, 49");
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.getLowerQuartile1(), is(new BigDecimal("15")));
        assertThat(data.getUpperQuartile1(), is(new BigDecimal("43")));
        data.setRawData("7, 15, 36, 39, 40, 41");
        dataService = new DataService(data);
        dataService.execute();
        assertThat(data.getLowerQuartile1(), is(new BigDecimal("15")));
        assertThat(data.getUpperQuartile1(), is(new BigDecimal("40")));
    }

    public void testDataServiceQuartile1Even() {
        Data data = new Data("7, 15, 36, 39, 40, 41");
        DataService dataService = new DataService(data);
        data = dataService.execute();
        assertThat(data.getLowerQuartile1(), is(new BigDecimal("15")));
        assertThat(data.getUpperQuartile1(), is(new BigDecimal("40")));
    }
}
