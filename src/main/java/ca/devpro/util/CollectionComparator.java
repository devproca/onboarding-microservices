package ca.devpro.util;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

@AllArgsConstructor
public class CollectionComparator<L, R> {

	private final Collection<L> leftCollection;
	private final Collection<R> rightCollection;

	public static <L, R> CollectionComparator<L, R> of(Collection<L> leftCollection, Collection<R> rightCollection) {
		return new CollectionComparator<>(leftCollection, rightCollection);
	}

	public final CollectionComparatorResult<L, R> compareWith(BiPredicate<L, R> areEqual) {
		CollectionComparatorResult<L, R> result = new CollectionComparatorResult<>(this.leftCollection, this.rightCollection);

		for (R right : rightCollection) {
			boolean exists = false;
			for (L left : leftCollection) {
				if (areEqual.test(left, right)) {
					exists = true;
					result.getExists().put(left, right);
					break;
				}
			}

			if (!exists) {
				result.getAdded().add(right);
			}
		}

		for (L left : leftCollection) {
			boolean exists = false;
			for (R right : rightCollection) {
				if (areEqual.test(left, right)) {
					exists = true;
					break;
				}
			}

			if (!exists) {
				result.getRemoved().add(left);
			}
		}

		return result;
	}

	@Value
	public static final class CollectionComparatorResult<L, R> {
		private final Collection<L> left;
		private final Collection<R> right;
		private final Set<L> removed = new LinkedHashSet<>();
		private final Map<L, R> exists = new LinkedHashMap<>();
		private final Set<R> added = new LinkedHashSet<>();

		public CollectionComparatorResult<L, R> ifRemoved(Consumer<L> removed) {
			this.removed.forEach(removed);
			return this;
		}

		public CollectionComparatorResult<L, R> ifExists(BiConsumer<L, R> merged) {
			this.exists.entrySet().forEach(m -> merged.accept(m.getKey(), m.getValue()));
			return this;
		}

		public CollectionComparatorResult<L, R> ifAdded(Consumer<R> added) {
			this.added.forEach(added);
			return this;
		}
	}

}
