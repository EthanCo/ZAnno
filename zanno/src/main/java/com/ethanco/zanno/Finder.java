package com.ethanco.zanno;


import java.util.List;

public enum Finder {
    SUBSCRIPTION {
        @Override
        public String destory(List<FieldBean> fields) {
            StringBuilder sb = new StringBuilder();

            for (FieldBean field : fields) {
                String fieldName = field.getFieldName();
                sb.append("if (target." + fieldName + " != null && !target." + fieldName + ".isUnsubscribed()) {\n");
                sb.append("    target." + fieldName + ".unsubscribe();\n");
                sb.append("}\n");
            }

            return sb.toString();
        }
    };

    public abstract String destory(List<FieldBean> fields);
}
