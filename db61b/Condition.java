package db61b;

import static db61b.Utils.error;

import java.util.List;

/** Represents a single 'where' condition in a 'select' command.
 *  @author Sam Khano */
class Condition {

    /** Internally, we represent our relation as a 3-bit value whose
     *  bits denote whether the relation allows the left value to be
     *  greater than the right (GT), equal to it (EQ),
     *  or less than it (LT). */
    private static final int GT = 1, EQ = 2, LT = 4;

    /** A Condition representing COL1 RELATION COL2, where COL1 and COL2
     *  are column designators. and RELATION is one of the
     *  strings "<", ">", "<=", ">=", "=", or "!=". */
    Condition(Column col1, String relation, Column col2) {
        _col1 = col1;
        _col2 = col2;
        if (relation.equals("<")) {
            _relation = LT;
        } else if (relation.equals(">")) {
            _relation = GT;
        } else if (relation.equals("<=")) {
            _relation = LT + EQ;
        } else if (relation.equals(">=")) {
            _relation = GT + EQ;
        } else if (relation.equals("=")) {
            _relation = EQ;
        } else if (relation.equals("!=")) {
            _relation = GT - GT;
        } else {
            throw error("Unidentified relation operator");
        }
    }

    /** A Condition representing COL1 RELATION 'VAL2', where COL1 is
     *  a column designator, VAL2 is a literal value (without the
     *  quotes), and RELATION is one of the strings "<", ">", "<=",
     *  ">=", "=", or "!=".
     */
    Condition(Column col1, String relation, String val2) {
        this(col1, relation, new Literal(val2));
    }

    /** Assuming that ROWS are rows from the respective tables from which
     *  my columns are selected, returns the result of performing the test I
     *  denote. */
    boolean test() {
        int compare = _col1.value().compareTo(_col2.value());
        int o = GT - GT;
        int gOrEq = GT + EQ;
        int lOrEq = LT + EQ;
        if (compare > 0) {
            return _relation == GT || _relation == gOrEq || _relation == o;
        } else if (compare < 0) {
            return _relation == LT || _relation == lOrEq || _relation == o;
        } else {
            return _relation == EQ || _relation == lOrEq || _relation == gOrEq;
        }

    }

    /** Return true iff all CONDITIONS are satisfied. */
    static boolean test(List<Condition> conditions) {
        for (Condition cond : conditions) {
            if (!cond.test()) {
                return false;
            }
        }
        return true;
    }

    /** The first column to check condition with. */
    private Column _col1;
    /** The second column to check condition with. */
    private Column _col2;
    /** The conditional relation. */
    private int _relation;
}
