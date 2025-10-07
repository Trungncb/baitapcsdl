import java.util.*;

interface PhuongThucThanhToan {
    void thanhToan(double soTien, String tenKhachHang);
}

class ThanhToanTienMat implements PhuongThucThanhToan {
    @Override
    public void thanhToan(double soTien, String tenKhachHang) {
        System.out.println("Khách hàng: " + tenKhachHang +
                ". Tổng tiền: " + soTien +
                ". Thanh toán tiền mặt thành công.");
    }
}

class ThanhToanTheTinDung implements PhuongThucThanhToan {
    @Override
    public void thanhToan(double soTien, String tenKhachHang) {
        System.out.println("Khách hàng: " + tenKhachHang +
                ". Tổng tiền: " + soTien +
                ". Thanh toán bằng thẻ tín dụng thành công.");
    }
}

class ThanhToanMomo implements PhuongThucThanhToan {
    @Override
    public void thanhToan(double soTien, String tenKhachHang) {
        System.out.println("Khách hàng: " + tenKhachHang +
                ". Tổng tiền: " + soTien +
                ". Thanh toán qua ví Momo thành công.");
    }
}

abstract class SanPham {
    protected String maSanPham;
    protected String tenSanPham;
    protected double gia;
    protected String loai;

    public SanPham(String maSanPham, String tenSanPham, double gia, String loai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.loai = loai;
    }

    public double getGia() {
        return gia;
    }

    public abstract String toString();
}

class SanPhamDienTu extends SanPham {
    private String imei;
    private int thoiGianBaoHanh;

    public SanPhamDienTu(String maSanPham, String tenSanPham, double gia, String imei, int thoiGianBaoHanh) {
        super(maSanPham, tenSanPham, gia, "Điện tử");
        this.imei = imei;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    @Override
    public String toString() {
        return tenSanPham + " (IMEI: " + imei + ", bảo hành: " + thoiGianBaoHanh + " tháng)";
    }
}

class SanPhamThucPham extends SanPham {
    private String hanSuDung;

    public SanPhamThucPham(String maSanPham, String tenSanPham, double gia, String hanSuDung) {
        super(maSanPham, tenSanPham, gia, "Thực phẩm");
        this.hanSuDung = hanSuDung;
    }

    @Override
    public String toString() {
        return tenSanPham + " (HSD: " + hanSuDung + ")";
    }
}

class DonHang {
    private String tenKhachHang;
    private List<SanPham> danhSachSanPham = new ArrayList<>();
    private PhuongThucThanhToan phuongThucThanhToan;

    public DonHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public void themSanPham(SanPham sp) {
        danhSachSanPham.add(sp);
    }

    public void setPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public double tinhTongTien() {
        return danhSachSanPham.stream().mapToDouble(SanPham::getGia).sum();
    }

    public void thanhToan() {
        if (phuongThucThanhToan == null) {
            System.out.println("Chưa chọn phương thức thanh toán!");
            return;
        }
        double tong = tinhTongTien();
        phuongThucThanhToan.thanhToan(tong, tenKhachHang);
    }
}


public class Main {
    public static void main(String[] args) {
 
        SanPham sp1 = new SanPhamDienTu("E01", "Laptop ASUS", 15000000, "1234567890", 24);
        SanPham sp2 = new SanPhamThucPham("F01", "Bánh quy Oreo", 30000, "2025-12-01");
        SanPham sp3 = new SanPhamThucPham("F02", "Sữa tươi Vinamilk", 25000, "2025-11-15");


        DonHang dh1 = new DonHang("Nguyễn Văn A");
        dh1.themSanPham(sp1);
        dh1.themSanPham(sp2);
        dh1.setPhuongThucThanhToan(new ThanhToanTienMat());
        dh1.thanhToan();

        System.out.println();

  
        DonHang dh2 = new DonHang("Nguyễn Văn B");
        dh2.themSanPham(sp2);
        dh2.themSanPham(sp3);
        dh2.setPhuongThucThanhToan(new ThanhToanTheTinDung());
        dh2.thanhToan();

        System.out.println();

        DonHang dh3 = new DonHang("Trần Thị C");
        dh3.themSanPham(sp1);
        dh3.setPhuongThucThanhToan(new ThanhToanMomo());
        dh3.thanhToan();
    }
}
