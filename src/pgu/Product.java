package pgu;

public class Product {

    public String reference;
    public String designation;
    public String weight;
    public String width;
    public String depth;
    public String height;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (reference == null ? 0 : reference.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (reference == null) {
            if (other.reference != null) {
                return false;
            }
        } else if (!reference.equals(other.reference)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product [reference=" + reference + ", designation=" + designation + ", weight=" + weight + ", width="
                + width + ", depth=" + depth + ", height=" + height + "]";
    }

}
