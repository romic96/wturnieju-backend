package pl.wturnieju.model.generic;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.wturnieju.model.IProfile;

@Data
@RequiredArgsConstructor
public abstract class TournamentTableRow<T extends TournamentTableRow> implements Comparable<T> {

    protected final IProfile profile;

    protected Double points;

    protected Integer wins;

    protected Integer draws;

    protected Integer loses;

    protected Integer games;

    @Override
    public int compareTo(@NonNull T o) {
        for (Supplier<Integer> comparator : getComparators(o)) {
            int result = comparator.get();
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    protected abstract List<Supplier<Integer>> getComparators(T o);
}
