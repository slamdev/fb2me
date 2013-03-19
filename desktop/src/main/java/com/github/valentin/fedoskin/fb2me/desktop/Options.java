package com.github.valentin.fedoskin.fb2me.desktop;

import java.util.HashSet;
import java.util.Set;

public class Options {

    public static class StageSize implements Comparable<StageSize> {

        public final double h;

        public final Class<?> type;

        public final double w;

        public final double x;

        public final double y;

        public StageSize() {
            this(null);
        }

        public StageSize(Class<?> type) {
            this(type, 0, 0, 0, 0);
        }

        public StageSize(Class<?> type, double w, double h, double x, double y) {
            this.type = type;
            this.w = w;
            this.h = h;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(StageSize o) {
            int dH = Double.compare(h, o.h);
            int dW = Double.compare(w, o.w);
            int dX = Double.compare(x, o.x);
            int dY = Double.compare(y, o.y);
            return dH + dW + dX + dY;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            StageSize other = (StageSize) obj;
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else if (!type.getName().equals(other.type.getName())) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (type == null ? 0 : type.getName().hashCode());
            return result;
        }

        @Override
        public String toString() {
            return "StageSize [type=" + type + ", h=" + h + ", w=" + w + ", x=" + x + ", y=" + y + "]";
        }
    }

    private String language = "";

    private Set<StageSize> stageSizes = new HashSet<>();

    public String getLanguage() {
        return language;
    }

    public Set<StageSize> getStageSizes() {
        return stageSizes;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStageSizes(Set<StageSize> stageSizes) {
        this.stageSizes = stageSizes;
    }
}