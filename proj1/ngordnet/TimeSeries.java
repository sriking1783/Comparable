package ngordnet;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.Collection;
import java.util.TreeSet;
import java.util.ArrayList;
public class TimeSeries<T extends Number> extends TreeMap<Integer, T>{

    public TimeSeries()  {
       this.clear();
    }

    public Collection<Number> years() {
        TreeSet<Number> t = new TreeSet<Number>();
        for(Integer year : keySet()) {
            t.add(year);
        }

        return t;
    }

    public Collection<Number> data() {
        ArrayList<Number> t = new ArrayList<Number>();
        T temp;

        for(Number year: years()) {
            temp = get((Integer) year);
            t.add((Number) temp);
        }
        return t;
    }

    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> t = new TimeSeries<Double>();
        Collection<Number> years = years();
        years.addAll(ts.years());

        for(Number year : years) {
            T thisvalue = this.get(year);
            Number tsvalue = ts.get(year);
            if(tsvalue == null) {
                t.put((Integer) year, thisvalue.doubleValue());
            } else if(thisvalue == null) {
                  t.put((Integer) year, tsvalue.doubleValue());
            }
            else {
                t.put((Integer) year, tsvalue.doubleValue() + thisvalue.doubleValue());
            }
        }

        return t;
    }

    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> t = new TimeSeries<Double>();
        for(Number year : years()) {
            T thisvalue = this.get(year);
            Number tsvalue  = ts.get(year);
            if(tsvalue == null) {
                throw new IllegalArgumentException();
            }
            t.put((Integer) year, thisvalue.doubleValue() / tsvalue.doubleValue());
        }
        return t;
    }


}
