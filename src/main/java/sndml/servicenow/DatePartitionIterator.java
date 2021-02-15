package sndml.servicenow;

import java.util.Iterator;

/**
 * Iterate through the ranges of a partition backwards, starting with
 * the most recent, and ending with the earliest.
 *
 */
public class DatePartitionIterator implements Iterator<DateTimeRange> {
	
	final DateTimeRange range;
	final Interval interval;
	final DateTimeRange newest;
	final DateTimeRange oldest;
	final int size;
	DateTimeRange current;

	public DatePartitionIterator(DateTimeRange range, Interval interval) {
		assert range != null;
		assert interval != null;
		if (range.getStart() == null)
			throw new IllegalArgumentException("start date is null");
		if (range.getEnd() == null)
			throw new IllegalArgumentException("end date is null");
		if (range.getEnd().compareTo(range.getStart()) < 0)
			throw new IllegalArgumentException("end date is before start date");
		this.range = range;
		this.interval = interval;
		if (range.getEnd().compareTo(range.getStart()) == 0) {
			this.newest = null;
			this.oldest = null;
			this.size = 0;
		}
		else {
			DateTime end = range.getEnd();
			DateTime endTrunc = end.truncate(interval);
			if (endTrunc.compareTo(end) < 0) end = endTrunc.incrementBy(interval);
			DateTime start = end.decrementBy(interval);
			assert start.compareTo(end) < 0;
			this.newest = new DateTimeRange(start, end);
			int size = 1;
			while (start.compareTo(range.getStart()) > 0) {
				size += 1;
				end = start;
				start = end.decrementBy(interval);
				assert start.compareTo(end) < 0;
			}
			this.oldest = new DateTimeRange(start, end);
			this.size = size;
		}
	}

	@Override
	public boolean hasNext() {
		return (current == null) ? size > 0 :
			current.getStart().compareTo(range.getStart()) > 0;
	}

	@Override
	public DateTimeRange next() {
		if (current == null) 
			current = newest;
		else {
			DateTime end = current.getStart();
			DateTime start = end.decrementBy(interval);
			assert start.compareTo(end) < 0;
			current = new DateTimeRange(start, end);
		}
		assert current != null;
		assert current.getStart().compareTo(current.getEnd()) < 0;
		return current;
	}

}
