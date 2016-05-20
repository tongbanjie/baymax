package com.tongbanjie.baymax.jdbc.merge;

public class MergeColumn {

    private String      columnName;
    private int         columnIndex;
	private MergeType   mergeType;

    private String      avgSumColumnName;
    private String      avgCountCoumnName;

	public MergeColumn(String columnName, int columnIndex, MergeType mergeType) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.mergeType = mergeType;
    }

    public MergeColumn(String columnName, int columnIndex, MergeType mergeType, String avgSumColumnName, String avgCountCoumnName) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.mergeType = mergeType;
        this.avgSumColumnName = avgSumColumnName;
        this.avgCountCoumnName = avgCountCoumnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public MergeType getMergeType() {
        return mergeType;
    }

    public String getAvgSumColumnName() {
        return avgSumColumnName;
    }

    public String getAvgCountCoumnName() {
        return avgCountCoumnName;
    }

    public static MergeType buildMergeType(String mergeType) {
		String upper = mergeType.toUpperCase();
		if (upper.startsWith("COUNT")) {
			return MergeType.MERGE_COUNT;
		} else if (upper.startsWith("SUM")) {
			return MergeType.MERGE_SUM;
		} else if (upper.startsWith("MIN")) {
			return MergeType.MERGE_MIN;
		} else if (upper.startsWith("MAX")) {
			return MergeType.MERGE_MAX;
		}else if (upper.startsWith("AVG")) {
            return MergeType.MERGE_AVG;
        }else {
			return MergeType.MERGE_UNSUPPORT;
		}
	}

    /**
     * 聚合函数合并类型
     */
    public enum MergeType{
        MERGE_COUNT,
        MERGE_SUM,
        MERGE_MIN,
        MERGE_MAX,
        MERGE_AVG,
        MERGE_NOMERGE,
        MERGE_UNSUPPORT;
    }
}