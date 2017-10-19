package com.leibangzhu.starters.common;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumTypeHandler extends BaseTypeHandler<IEnum> {

    private Class<IEnum> type;

    public EnumTypeHandler(Class<IEnum> type) {
        if(null == type) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnum parameter, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public IEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {

        if(rs.wasNull()) {
            return null;
        }

        int code = rs.getInt(columnName);
        return convert(code);
    }

    @Override
    public IEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        if(rs.wasNull()) {
            return null;
        }

        int code = rs.getInt(columnIndex);
        return convert(code);
    }

    @Override
    public IEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        if(cs.wasNull()) {
            return null;
        }

        int code = cs.getInt(columnIndex);
        return convert(code);
    }

    private IEnum convert(int code) {
        IEnum[] enums = type.getEnumConstants();
        for(IEnum e : enums) {
            if(code == e.getCode()) {
                return e;
            }
        }
        return null;
    }
}
