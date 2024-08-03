use master
go
Create database QL_HomeStay
Go
Use QL_HomeStay;
Go
--------------------------------------------------TABLE--------------------------------------------------
create table TaiKhoan
(
	TentaiKhoan	varchar(100) not null,
	MatKhau		varchar(100),
	Quyen		nvarchar(100),
)

create table QuanHuyen
(
	MaQH	int identity(1,1) not null,
	TenQH	nvarchar(100),
	constraint uni_tenqh unique(TenQH),
)
Create table NhanVien
(
	MaNV		int identity(1,1) not null,
	TenNV		nvarchar(100),
	GioiTinh	nvarchar(3) check (GioiTinh in(N'Nam', N'Nữ')),
	NgaySinh	date check(NgaySinh < getdate()),
	SDT			varchar(11),
	MaQH		int not null,
	TaiKhoan	varchar(100),
);

Create table KhachHang
(
	MaKH		int identity(1,1) not null,
	TenKH		nvarchar(100),
	GioiTinh	nvarchar(3) check (GioiTinh in(N'Nam', N'Nữ')),
	NgaySinh	date check(NgaySinh < getdate()),
	SDT			varchar(11),
	CCCD		varchar(13),
	MaQH		int not null,
	constraint UNI_CCCD unique(CCCD)
);
create table LoaiPhong(
	MaLoaiPhong		int identity(1,1) not null,
	TenLoaiPhong	nvarchar(100),
	DonGia			decimal(18,2) check(DonGia > 0)
);
Create table Phong(
	MaPhong		int identity(1,1) not null,
	TenPhong	nvarchar(100),
	MaLoaiPhong	int,
	TienNghi	nvarchar(100), --Tiện nghi
	TrangThai	nvarchar(100), --Có 2 trạng thái: Đang trống, Đã được đặt
);


CREATE TABLE DatPhong (
    MaDP INT IDENTITY(1,1) NOT NULL,
    NgayDatPhong DATE,
    NgayTraPhong DATE,
    SoNguoi INT CHECK (SoNguoi > 0),
    MaKH INT NOT NULL,
    MaPhong INT NOT NULL,
    ThanhToan NVARCHAR(50) NOT NULL DEFAULT N'Chưa thanh toán',
    CONSTRAINT Chk_NgayTraPhong CHECK (NgayTraPhong > NgayDatPhong)
);


Create table HoaDon(
	MaHD		int identity(1,1) not null,
	NgayLapHD	date,
	TongTien	decimal(18, 2) default 0,
	MaNV		int not null,
	MaKH		int not null,
	MaPhong		int not null,
);

Create table LoaiDichVu(
	MaLoaiDV	int identity(1,1) not null,
	TenLoaiDV	nvarchar(100),
);

Create table DichVu(
	MaDV		int identity(1,1) not null,
	TenDV		nvarchar(100),
	DonGia		decimal(18, 2) check(DonGia > 0),
	MaLoaiDV	int not null,
);

Create table PhucVu(
	MaPV	int identity(1,1) not null,
	SoLuong	int check(SoLuong > 0),
	MaKH	int not null,
	MaDV	int not null,
);
--------------------------------------------------PRIMARY KEY--------------------------------------------------
Alter table NhanVien add constraint PK_NV primary key(MaNV);
Alter table KhachHang add constraint PK_KH primary key(MaKH);
Alter table Phong add constraint PK_Phong primary key(MaPhong);
Alter table LoaiDichVu add constraint PK_LoaiDV primary key(MaLoaiDV);
Alter table DatPhong add constraint PK_DatPhong primary key(MaDP);
Alter table HoaDon add constraint PK_HD primary key(MaHD);
Alter table DichVu add constraint PK_DV primary key(MaDV);
Alter table PhucVu add constraint Pk_PV primary key(MaPV);
Alter table QuanHuyen add constraint PK_QH primary key(MaQH);
Alter table TaiKhoan add constraint PK_TK primary key(TenTaiKhoan);
Alter table LoaiPhong add constraint PK_LP primary key(MaLoaiPhong);
--------------------------------------------------FOREIGN KEY--------------------------------------------------
Alter table DatPhong add constraint FK_DP_KH foreign key(MaKH) references KhachHang(MaKH),
						 constraint FK_DP_Phong foreign key(MaPhong) references Phong(MaPhong);
Alter table HoaDon add constraint FK_HD_NV foreign key(MaNV) references NhanVien(MaNV),
					   constraint FK_HD_KH foreign key(MaKH) references KhachHang(MaKH),
					   constraint FK_HD_Phong foreign key(MaPhong) references Phong(MaPhong);
Alter table DichVu add constraint FK_DV_LoaiDV foreign key(MaLoaiDV) references LoaiDichVu(MaLoaiDV);
ALter table PhucVu add constraint FK_PV_KH foreign key(MaKH) references KhachHang(MaKH),
					   constraint FK_PV_DV foreign key(MaDV) references DichVu(MaDV);
Alter table NhanVien add constraint FK_NV_QH foreign key(MAQH) references QuanHuyen(MaQH),
						 constraint FK_NV_TK foreign key (TaiKhoan) references TaiKhoan(TenTaiKhoan);	
Alter table KhachHang add constraint FK_KH_QH foreign key (MAQH) references QuanHuyen(MaQH);
Alter table Phong add constraint FK_PH_LPH foreign key (MaLoaiPhong) references LoaiPhong(MaLoaiPhong);
--------------------------------------------------TRIGGER--------------------------------------------------
--1. Cập nhật tổng tiền cho bảng hoá đơn
CREATE TRIGGER trg_CapNhatTongTien
ON HoaDon
FOR INSERT, UPDATE
AS
BEGIN
    -- Khai báo các biến
    DECLARE @MaKH INT, @MaPhong INT, @DonGiaPhong DECIMAL(18, 2), @TongTienDV DECIMAL(18, 2)
    DECLARE @NgayDatPhong DATE, @NgayTraPhong DATE, @SoNgayO INT, @TongTienPhong DECIMAL(18, 2)
    DECLARE @MaLoaiPhong INT, @DonGia DECIMAL(18, 2)

    -- Tạo một con trỏ để duyệt qua từng dòng trong tập hợp các dòng mới được thêm hoặc cập nhật
    DECLARE cur CURSOR FOR SELECT MaKH FROM inserted
    OPEN cur
    FETCH NEXT FROM cur INTO @MaKH

    -- Duyệt qua từng dòng
    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Lấy MaPhong, NgayDatPhong, và NgayTraPhong từ bảng DatPhong dựa trên MaKH
        SELECT @MaPhong = MaPhong, @NgayDatPhong = NgayDatPhong, @NgayTraPhong = NgayTraPhong
        FROM DatPhong
        WHERE MaKH = @MaKH

        -- Tính số ngày ở
        SET @SoNgayO = DATEDIFF(DAY, @NgayDatPhong, @NgayTraPhong)

        -- Lấy MaLoaiPhong từ bảng Phong dựa trên MaPhong
        SELECT @MaLoaiPhong = MaLoaiPhong
        FROM Phong
        WHERE MaPhong = @MaPhong

        -- Lấy DonGia từ bảng LoaiPhong dựa trên MaLoaiPhong
        SELECT @DonGia = DonGia
        FROM LoaiPhong
        WHERE MaLoaiPhong = @MaLoaiPhong

        -- Tính tổng tiền phòng
        SET @TongTienPhong = @SoNgayO * @DonGia

        -- Tính tổng tiền dịch vụ = đơn giá x số lượng
        SELECT @TongTienDV = SUM(DichVu.DonGia * PhucVu.SoLuong)
        FROM PhucVu
        INNER JOIN DichVu ON PhucVu.MaDV = DichVu.MaDV
        WHERE PhucVu.MaKH = @MaKH

        -- Cập nhật TongTien trong bảng HoaDon = TongTienDV + TongTienPhong
        UPDATE HoaDon
        SET TongTien = (@TongTienDV + @TongTienPhong)
        WHERE MaKH = @MaKH

        -- Di chuyển đến dòng tiếp theo trong tập hợp inserted
        FETCH NEXT FROM cur INTO @MaKH
    END

    -- Đóng và hủy con trỏ
    CLOSE cur
    DEALLOCATE cur
END;
GO
--select * from HoaDon;
--select * from PhucVu;
--select * from DichVu;
--select * from Phong;
--Drop trigger trg_CapNhatTongTien;


--------------------------------------------------PROCEDURE--------------------------------------------------
--------------------------------------------------KHACH HANG-------------------------------------------------

CREATE PROCEDURE dsKhachHang AS
BEGIN
	SELECT kh.MaKH, kh.TenKH, kh.GioiTinh, kh.NgaySinh, kh.CCCD, qh.TenQH, kh.SDT
	FROM KhachHang kh
	INNER JOIN QuanHuyen qh ON kh.MaQH = qh.MaQH
END;
go

CREATE PROC themKhachHang
	@TenKH nvarchar(100),
	@GioiTinh nvarchar(3),
	@NgaySinh date,
	@SDT varchar(11),
	@CCCD varchar(13),
	@MaQH int
as
begin 
	insert into KhachHang(TenKH, GioiTinh, NgaySinh, SDT, CCCD, MaQH)
	values(@TenKH, @GioiTinh,@NgaySinh,@SDT, @CCCD, @MaQH)
end
go

create proc suaKhachHang
	@MaKH int,
	@TenKH nvarchar(100),
	@GioiTinh nvarchar(3),
	@NgaySinh date,
	@SDT varchar(11),
	@CCCD varchar(13),
	@MaQH int
as
begin
	update KhachHang
	set TenKH = @TenKH, GioiTinh= @GioiTinh, NgaySinh = @NgaySinh, SDT = @SDT, CCCD = @CCCD, MaQH = @MaQH
	where MaKH = @MaKH
end

go 

create proc xoaKhachHang
	@MaKH int
as
begin 
	delete KhachHang
	where MaKH = @MaKH
end;
go

create proc timKhachHang
	@TenKH nvarchar(100)
as
begin
	SELECT kh.MaKH, kh.TenKH, kh.GioiTinh, kh.NgaySinh, kh.CCCD, qh.TenQH, kh.SDT
	FROM KhachHang kh
	INNER JOIN QuanHuyen qh ON kh.MaQH = qh.MaQH
	where kh.TenKH like '%' +@TenKH+'%'
end
go

create proc dsCCCD
as
begin
	select kh.MaKH, kh.CCCD
	from KhachHang kh
	INNER JOIN QuanHuyen qh ON kh.MaQH = qh.MaQH
end
go
--------------------------------------------------QUAN HUYEN-------------------------------------------------
create proc dsQuanHuyen
as
begin 
	select * from QuanHuyen
end
go
--------------------------------------------------DICH VU---------------------------------------------------
create proc dsDichVu
as
begin
	select dv.MaDV, dv.TenDV, dv.DonGia, ldv.TenLoaiDV
	from DichVu dv
	INNER JOIN LoaiDichVu ldv on dv.MaLoaiDV = ldv.MaLoaiDV
end;
go

go
create proc themDichVu
	@TenDV nvarchar(100),
	@MaLoaiDV nvarchar(100),
	@DonGia decimal(18,2)
as
begin 
	insert into DichVu(TenDV,MaLoaiDV,DonGia)
	values (@TenDV,@MaLoaiDV,@DonGia)
end
go

create proc suaDichVu
	@MaDV int,
	@TenDV nvarchar(100),
	@DonGia decimal(18,2),
	@MaLoaiDV int
as
begin
	update DichVu
	set TenDV = @TenDV, DonGia = @DonGia, MaLoaiDV=@MaLoaiDV
	where MaDV = @MaDV
end;
go

create proc xoaDichVu
	@MaDV int
as
begin
	delete DichVu
	where MaDV = @MaDV
end;
go

create proc timKiemDV
	@TenDV nvarchar(100)
as
begin
	SELECT dv.MaDV, dv.TenDV, dv.DonGia, ldv.TenLoaiDV
	FROM DichVu dv
	INNER JOIN LoaiDichVu ldv ON dv.MaLoaiDV = ldv.MaLoaiDV
	where dv.TenDV like '%' +@TenDV+'%'
end
-------------------------------------------------LOAI DICH VU-----------------------------------------------
go
create proc dsLoaiDV
as
begin
	select * from LoaiDichVu
end
-----------------------------------------------------PHONG---------------------------------------------------
go
CREATE PROCEDURE dsPhong
AS
BEGIN
    SELECT 
        P.MaPhong AS 'Mã Phòng',
        P.TenPhong AS 'Tên Phòng',
        LP.TenLoaiPhong AS 'Tên Loại Phòng',
        LP.DonGia AS 'Đơn Giá',
        P.TienNghi AS 'Tiện Nghi',
        P.TrangThai AS 'Trạng Thái'
    FROM 
        Phong P
    INNER JOIN 
        LoaiPhong LP ON P.MaLoaiPhong = LP.MaLoaiPhong;
END;

go
create proc themPhong
	@TenPhong nvarchar(100),
	@MaLoaiPhong int,
	@TienNghi nvarchar(100),
	@TrangThai nvarchar(100)
as
begin
	insert into Phong(TenPhong,MaLoaiPhong,TienNghi,TrangThai)
	values(@TenPhong,@MaLoaiPhong,@TienNghi,@TrangThai)
end;

go
create proc suaPhong
	@MaPhong int,
	@TenPhong nvarchar(100),
	@MaLoaiPhong int,
	@TienNghi nvarchar(100),
	@TrangThai nvarchar(100)
as
begin
	update Phong
	set TenPhong = @TenPhong, MaLoaiPhong = @MaLoaiPhong, TienNghi = @TienNghi, TrangThai = @TrangThai
	where MaPhong = @MaPhong
end;

go
create proc xoaPhong
	@MaPH int
as
begin
	delete Phong
	where MaPhong = @MaPH
end;
go

go
create proc timKiemPH
	@TenPhong nvarchar(100)
as
begin
	SELECT ph.MaPhong, ph.TenPhong, lp.TenLoaiPhong, lp.DonGia, ph.TienNghi, ph.TrangThai
	FROM Phong ph
	INNER JOIN LoaiPhong lp ON ph.MaLoaiPhong = lp.MaLoaiPhong
	where ph.TenPhong like '%' +@TenPhong+'%'
end
go
create proc ktphong
as
begin
	select MaPhong,TenPhong,TrangThai
	from Phong
end;

exec ktphong
go
create proc dsPhongTrong 
as
begin 
	select p.MaPhong , p.TenPhong, lp.TenLoaiPhong,lp.DonGia, p.TienNghi, p.TrangThai
	from Phong p
	inner join LoaiPhong lp on p.MaLoaiPhong = lp.MaLoaiPhong
	where p.TrangThai =N'Đang trống'
end
exec dsPhongTrong

-------------------------------------------------LOAI PHONG-----------------------------------------------
go
create proc dsLoaiPhong
as
begin	
	select * from LoaiPhong
end;

-------------------------------------------------NHAN VIEN------------------------------------------------
go
CREATE PROCEDURE dsNhanVien AS
BEGIN
	SELECT 
		nv.MaNV, 
		nv.TenNV, 
		nv.GioiTinh, 
		nv.NgaySinh, 
		nv.SDT, 
		qh.TenQH, 
		tk.Quyen
	FROM 
		NhanVien nv
	INNER JOIN 
		QuanHuyen qh ON nv.MaQH = qh.MaQH
	INNER JOIN 
		TaiKhoan tk ON nv.TaiKhoan = tk.TenTaiKhoan;
END;
go
create proc themNhanVien
	@TenNV nvarchar(100),
	@NgaySinh date,
	@SDT nvarchar(10),
	@MaQH int,
	@GioiTinh nvarchar(3),
	@TenTaiKhoan varchar(100),
	@MatKhau varchar(100),
	@Quyen nvarchar(100)
as
begin
	insert into TaiKhoan(TentaiKhoan,MatKhau,Quyen)
	values(@TenTaiKhoan, @MatKhau, @Quyen) 
	 
	insert into NhanVien (TenNV,NgaySinh,GioiTinh,MaQH,SDT,TaiKhoan)
	values (@TenNV,@NgaySinh,@GioiTinh,@MaQH,@SDT,@TenTaiKhoan)
end
go
create proc suaNhanVien
	@MaNV int,
	@TenNV nvarchar(100),
	@NgaySinh date,
	@SDT nvarchar(10),
	@GioiTinh nvarchar(3),
	@MaQH int
as
begin
	update NhanVien 
	set TenNV = @TenNV, NgaySinh = @NgaySinh, SDT = @SDT, GioiTinh = @GioiTinh, MaQH = @MaQH
	where MaNV= @MaNV
end;
go
CREATE PROCEDURE xoaNhanVien
	@MaNV int
AS
BEGIN
	-- Kiểm tra xem nhân viên có tồn tại không
	IF NOT EXISTS (SELECT 1 FROM NhanVien WHERE MaNV = @MaNV)
	BEGIN
		RAISERROR('Nhân viên không tồn tại.', 16, 1);
		RETURN;
	END

	-- Xóa nhân viên
	DELETE FROM NhanVien
	WHERE MaNV = @MaNV;

	-- Xóa tài khoản tương ứng với nhân viên
	DELETE FROM TaiKhoan
	WHERE TentaiKhoan NOT IN (SELECT TaiKhoan FROM NhanVien);
END;

go
CREATE PROCEDURE timkiemNV 
	@TenNV nvarchar(100)
AS
BEGIN
	SELECT 
		nv.MaNV, 
		nv.TenNV, 
		nv.GioiTinh, 
		nv.NgaySinh, 
		nv.SDT, 
		qh.TenQH, 
		tk.Quyen
	FROM 
		NhanVien nv
	INNER JOIN 
		QuanHuyen qh ON nv.MaQH = qh.MaQH
	INNER JOIN 
		TaiKhoan tk ON nv.TaiKhoan = tk.TenTaiKhoan
	where nv.TenNV like '%' +@TenNV+'%'
END;
go
CREATE PROCEDURE layMaNV
	@username varchar(100)
AS
BEGIN
	SELECT NV.MaNV
	FROM NhanVien NV
	INNER JOIN TaiKhoan TK ON NV.TaiKhoan = TK.TentaiKhoan
	WHERE TK.TentaiKhoan = @username;
END;
go
-----------------------------------------------TaiKhoan------------------------------------------------


CREATE PROCEDURE getQuyen(@username varchar(100))
AS
BEGIN
    SELECT Quyen
    FROM TaiKhoan
    WHERE TentaiKhoan = @username;
END;
go

CREATE PROCEDURE KiemTraTaiKhoan
    @Username NVARCHAR(50)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM TaiKhoan WHERE TentaiKhoan = @Username)
    BEGIN
        SELECT 1 AS Result;
    END
    ELSE
    BEGIN
        SELECT 0 AS Result;
    END
END
go


CREATE PROCEDURE KiemTraMatKhau
    @Username NVARCHAR(50),
    @Password NVARCHAR(50)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM TaiKhoan WHERE TentaiKhoan = @Username AND MatKhau = @Password)
    BEGIN
        SELECT 1 AS Result;
    END
    ELSE
    BEGIN
        SELECT 0 AS Result;
    END
END

-----------------------------------------------DAT PHONG-----------------------------------------------
go
CREATE PROCEDURE dsDatPhong
AS
BEGIN
    SELECT 
        dp.MaDP,
		p.MaPhong,
        kh.CCCD,
        p.TenPhong,
        lp.DonGia,
        dp.NgayDatPhong,
        dp.NgayTraPhong,
        dp.SoNguoi
    FROM 
        DatPhong dp
    INNER JOIN 
        KhachHang kh ON dp.MaKH = kh.MaKH
    INNER JOIN 
        Phong p ON dp.MaPhong = p.MaPhong
    INNER JOIN 
        LoaiPhong lp ON p.MaLoaiPhong = lp.MaLoaiPhong
	where 
		dp.ThanhToan = N'Chưa thanh toán'
END
exec dsDatPhong
go
CREATE PROC xuLyDatPhong
	@MaKH INT,
	@MaPH INT,
	@SoNguoi INT,
	@NgayDat DATE,
	@NgayTra DATE
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION;

		-- Thêm thông tin đặt phòng
		INSERT INTO DatPhong(NgayDatPhong, NgayTraPhong, MaKH, MaPhong, SoNguoi)
		VALUES (@NgayDat, @NgayTra, @MaKH, @MaPH, @SoNguoi);

		-- Cập nhật trạng thái phòng
		UPDATE Phong
		SET TrangThai = N'Đã được đặt'
		WHERE MaPhong = @MaPH;

		COMMIT;
	END TRY
	BEGIN CATCH
		-- Xử lý lỗi (nếu có)
		ROLLBACK;
		THROW;
	END CATCH;
END


go 
CREATE PROCEDURE XuLyDoiPhong
    @MaDP INT,
    @MaP1 INT,
    @MaP2 INT
AS
BEGIN
    BEGIN TRANSACTION; -- Bắt đầu giao dịch

    BEGIN TRY
        -- Cập nhật MaPhong trong bảng DatPhong
        UPDATE DatPhong
        SET MaPhong = @MaP2
        WHERE MaDP = @MaDP;

        -- Cập nhật trạng thái của phòng cũ (MaP1)
        UPDATE Phong
        SET TrangThai = N'Đang trống'
        WHERE MaPhong = @MaP1;

        -- Cập nhật trạng thái của phòng mới (MaP2)
        UPDATE Phong
        SET TrangThai = N'Đã được đặt'
        WHERE MaPhong = @MaP2;

        COMMIT; -- Hoàn thành giao dịch
    END TRY
    BEGIN CATCH
        ROLLBACK; -- Hoàn tác giao dịch nếu có lỗi
        THROW; -- Ném lỗi để xử lý ở lớp gọi
    END CATCH
END;
go
CREATE PROCEDURE thongTinChiTietPhong
    @MaPhong INT
AS
BEGIN
    SELECT
		KH.MaKH,
        KH.TenKH,
        DP.SoNguoi,
        LP.DonGia,
        DP.NgayDatPhong,
        DP.NgayTraPhong
    FROM 
        DatPhong DP
        JOIN KhachHang KH ON DP.MaKH = KH.MaKH
        JOIN Phong P ON DP.MaPhong = P.MaPhong
        JOIN LoaiPhong LP ON P.MaLoaiPhong = LP.MaLoaiPhong
    WHERE 
        DP.MaPhong = @MaPhong and DP.ThanhToan = N'Chưa thanh toán'
END;

go

---------------------------------------PHUC VU-----------------------------------------
--go
CREATE PROCEDURE ThemPhucVu
    @MaDV INT,
    @MaKH INT,
    @SoLuong INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM PhucVu WHERE MaKH = @MaKH AND MaDV = @MaDV)
    BEGIN
        UPDATE PhucVu
        SET SoLuong = SoLuong + @SoLuong
        WHERE MaKH = @MaKH AND MaDV = @MaDV;
    END
    ELSE
    BEGIN
        INSERT INTO PhucVu (MaKH, MaDV, SoLuong)
        VALUES (@MaKH, @MaDV, @SoLuong);
    END
END;
go
create PROCEDURE thongTinPhucVu
    @MaPhong INT,
    @MaKH INT
AS
BEGIN
    SELECT 
        DV.TenDV,
        PV.SoLuong,
        DV.DonGia
    FROM 
        PhucVu PV
        JOIN DichVu DV ON PV.MaDV = DV.MaDV
        JOIN DatPhong DP ON PV.MaKH = DP.MaKH AND PV.MaKH = @MaKH
    WHERE 
        DP.MaPhong = @MaPhong
        --DP.ThanhToan = N'Chưa thanh toán';
END;

-------------------------------------------------------HOADON-----------------------------
go
CREATE PROCEDURE sp_ThanhToan
    @MaKH INT,
    @MaNV INT,
    @MaPhong INT,
    @NgayLapHD DATE
AS
BEGIN
    DECLARE @TongTien DECIMAL(18, 2);
    DECLARE @NgayDatPhong DATE;
    DECLARE @NgayTraPhong DATE;
    DECLARE @DonGia DECIMAL(18, 2);
    DECLARE @SoLuong INT;
    DECLARE @DonGiaDV DECIMAL(18, 2);

    -- Lấy ngày đặt phòng, ngày trả phòng, và đơn giá phòng
    SELECT @NgayDatPhong = NgayDatPhong, @NgayTraPhong = NgayTraPhong, @DonGia = LP.DonGia
    FROM DatPhong DP
    JOIN Phong P ON DP.MaPhong = P.MaPhong
    JOIN LoaiPhong LP ON P.MaLoaiPhong = LP.MaLoaiPhong
    WHERE DP.MaKH = @MaKH AND DP.MaPhong = @MaPhong AND DP.ThanhToan = N'Chưa thanh toán';

    -- Tính tổng tiền cho phần thuê phòng
    SET @TongTien = DATEDIFF(DAY, @NgayDatPhong, @NgayTraPhong) * @DonGia;

    -- Lấy số lượng và đơn giá dịch vụ, sau đó cộng vào tổng tiền
    DECLARE cur CURSOR FOR
    SELECT SoLuong, DV.DonGia
    FROM PhucVu PV
    JOIN DichVu DV ON PV.MaDV = DV.MaDV
    WHERE PV.MaKH = @MaKH;
    OPEN cur;
    FETCH NEXT FROM cur INTO @SoLuong, @DonGiaDV;
    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @TongTien = @TongTien + @SoLuong * @DonGiaDV;
        FETCH NEXT FROM cur INTO @SoLuong, @DonGiaDV;
    END;
    CLOSE cur;
    DEALLOCATE cur;

    -- Thêm bản ghi mới vào bảng HoaDon
    INSERT INTO HoaDon (NgayLapHD, TongTien, MaNV, MaKH, MaPhong)
    VALUES (@NgayLapHD, @TongTien, @MaNV, @MaKH, @MaPhong);

    -- Cập nhật trạng thái phòng thành 'Đang trống'
    UPDATE Phong
    SET TrangThai = N'Đang trống'
    WHERE MaPhong = @MaPhong;

    -- Cập nhật trạng thái thanh toán thành 'Đã thanh toán'
    UPDATE DatPhong
    SET ThanhToan = N'Đã thanh toán'
    WHERE MaKH = @MaKH AND MaPhong = @MaPhong;
END;
go
CREATE PROCEDURE sp_ChiTietHoaDon
    @MaKH INT,
    @MaPhong INT
AS
BEGIN
    SELECT 
        HD.MaHD,
        NV.TenNV,
        HD.NgayLapHD,
        KH.TenKH,
        HD.MaPhong,
        DP.NgayDatPhong,
        DP.NgayTraPhong,
		HD.TongTien
    FROM 
        HoaDon HD
        JOIN NhanVien NV ON HD.MaNV = NV.MaNV
        JOIN KhachHang KH ON HD.MaKH = KH.MaKH
        JOIN DatPhong DP ON HD.MaKH = DP.MaKH AND HD.MaPhong = DP.MaPhong
    WHERE 
        HD.MaKH = @MaKH AND
        HD.MaPhong = @MaPhong;
END;

GO
CREATE PROCEDURE dsHoaDon
AS
BEGIN
    SELECT 
        HD.MaHD,
        HD.NgayLapHD,
        HD.TongTien,
        NV.MaNV,
        NV.TenNV,
        KH.MaKH,
        KH.TenKH,
        P.MaPhong,
        P.TenPhong
    FROM 
        HoaDon HD
        JOIN NhanVien NV ON HD.MaNV = NV.MaNV
        JOIN KhachHang KH ON HD.MaKH = KH.MaKH
        JOIN Phong P ON HD.MaPhong = P.MaPhong
    ORDER BY 
        HD.MaHD;
END;

GO
CREATE PROCEDURE dsHoaDon
AS
BEGIN
    SELECT 
        HD.MaHD as 'Mã Hóa Đơn',
        HD.NgayLapHD as 'Ngày Lập HĐ',
		HD.TongTien as 'Tổng Tiền',
        NV.MaNV as 'Mã NV',
        KH.MaKH as 'Mã KH',
        P.MaPhong as 'Mã Phòng'
    FROM 
        HoaDon HD
        JOIN NhanVien NV ON HD.MaNV = NV.MaNV
        JOIN KhachHang KH ON HD.MaKH = KH.MaKH
        JOIN Phong P ON HD.MaPhong = P.MaPhong
    ORDER BY 
        HD.MaHD;
END;
GO
-------------------------------------------------Thống kê
CREATE PROCEDURE GetYearFromHoaDon
AS
BEGIN
    SELECT DISTINCT YEAR(NgayLapHD) AS YearNumber FROM HoaDon ORDER BY YearNumber;
END
go
CREATE PROCEDURE GetMonthFromHoaDonByYear
    @Year INT
AS
BEGIN
    SELECT DISTINCT MONTH(NgayLapHD) AS MonthNumber
    FROM HoaDon
    WHERE YEAR(NgayLapHD) = @Year
    ORDER BY MonthNumber;
END
go
CREATE PROCEDURE TinhTongTienHoaDon
AS
BEGIN
    DECLARE @TongTienHoaDon DECIMAL(18, 2);
    SELECT @TongTienHoaDon = SUM(TongTien)
    FROM HoaDon;

    SELECT @TongTienHoaDon AS TongTienHoaDon;
END;
GO	

CREATE PROCEDURE TinhTongTienHoaDonTheoNam
    @Year INT
AS
BEGIN
    DECLARE @TongTienHoaDon DECIMAL(18, 2);

    SELECT @TongTienHoaDon = SUM(TongTien)
    FROM HoaDon
    WHERE YEAR(NgayLapHD) = @Year;

    SELECT @TongTienHoaDon AS TongTienHoaDon;
END;
GO

CREATE PROCEDURE TinhTongSoPhongDuocChoThue
	@Year INT
as
begin
	DECLARE @TongSoPhong int

	select  @TongSoPhong  = count(*)
	from HoaDon
	where YEAR(NgayLapHD) = @Year

	SELECT @TongSoPhong as TongSoPhong
end
go

CREATE PROCEDURE TinhTongTienDichVuTheoNam
    @Year INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TongTienDichVu DECIMAL(18, 2);

    SELECT @TongTienDichVu = SUM(pv.SoLuong * dv.DonGia)
    FROM PhucVu pv
    JOIN DichVu dv ON pv.MaDV = dv.MaDV
    JOIN HoaDon hd ON pv.MaKH = hd.MaKH
    WHERE YEAR(hd.NgayLapHD) = @Year;

    SELECT @TongTienDichVu AS TongTienDichVu;
END;

exec TinhTongTienDichVuTheoNam 2024

--------------------------------------------------DATA--------------------------------------------------
set dateformat dmy;
insert into QuanHuyen(TenQH) values
(N'Quận 1'),
(N'Thành phố Thủ Đức'),
(N'Quận 3'),
(N'Quận 4'),
(N'Quận 5'),
(N'Quận 6'),
(N'Quận 7'),
(N'Quận 8'),
(N'Quận 10'),
(N'Quận 11'),
(N'Quận 12'),
(N'Quận Bình Tân'),
(N'Quận Tân Bình'),
(N'Quận Tân Phú'),
(N'Quận Bình Thạnh'),
(N'Quận Phú Nhuận'),
(N'Quận Gò Vấp'),
(N'Huyện Hóc Môn'),
(N'Huyện Cần Giờ'),
(N'Huyện Nhà Bè'),
(N'Huyện Củ Chi'),
(N'Huyện Bình Chánh');


insert into TaiKhoan(TenTaiKhoan,MatKhau,Quyen) values
('staff1',123, N'Quản lý'),
('staff2',123, N'Nhân viên'),
('staff3',123, N'Nhân viên');


Insert into NhanVien(TenNV, GioiTinh, NgaySinh, MaQH, SDT, TaiKhoan) values
(N'Huỳnh Khánh Nam', N'Nam', '08/08/2003',1 ,'0396105269', 'staff1'),
(N'Trần Đức Thiện', N'Nam', '09/09/2003',2, '0976032507', 'staff2'),
(N'Trần Thành Luân', N'Nam', '02/04/2003',3, '0367512498', 'staff3');

Insert into KhachHang(TenKH, GioiTinh, NgaySinh, MaQH, SDT,CCCD) values
(N'Nguyễn Thế Bảo', N'Nam','13/02/2002', 1 ,'0998768998','092128312001'),
(N'Trần Quốc Phú', N'Nam','01/05/2002', 2, '0909876876','092128312002'),
(N'Phạm Tuấn Anh', N'Nam','05/12/2002', 3, '0998734234','092128312003'),
(N'Lê Khánh Duy', N'Nam','06/04/2002', 4, '0998734456','092128312004'),
(N'Nguyễn Ngọc Sương', N'Nữ','04/10/2002',5, '0772685679','092128312005'),
(N'Võ Khánh Nam', N'Nam','06/04/1995', 6, '0885123456','092128312006'),
(N'Nguyễn Tùng Văn', N'Nam','18/02/2003',7, '0802954986','092128312007'),
(N'Trần Văn Long', N'Nam','15/08/2000', 8, '0125648741','092128312008'),
(N'Phạm Quỳnh Như', N'Nữ','18/08/2000', 9, '0772512643','092128312009'),
(N'Nguyễn Phương Thảo', N'Nữ','28/04/2002', 10, '01256412985','092128312010'),
(N'Võ Minh Quân', N'Nam','27/12/2002', 11, '0365412573','092128312011'),
(N'Nguyễn Ngọc Khang', N'Nam','16/10/2002', 12, '0121495785','092128312012'),
(N'Trần Quang Kiệt', N'Nam','06/10/2002', 13, '0772528442','092128312013'),
(N'Nguyễn Nhật Hoàng', N'Nam','22/02/2002', 14, '0156202304','092128312014'),
(N'Nguyễn Thị Kim Ngân', N'Nữ','13/03/2002', 15, '0123456789','092128312015'),
(N'Trần Thị Minh Khai', N'Nữ','01/05/2002', 16, '0234567891','092128312016'),
(N'Phạm Thị Hoàng Oanh', N'Nữ','05/12/2002', 17, '0345678912','092128312017'),
(N'Lê Thị Thu Thảo', N'Nữ','06/04/2002', 18, '0456789123','092128312018'),
(N'Võ Thị Hồng Nhung', N'Nữ','04/10/2002', 19, '0567891234','092128312019'),
(N'Nguyễn Thị Ngọc Trâm', N'Nữ','10/08/2002', 20, '0678912345','092128312020'),
(N'Trần Thị Mỹ Lệ', N'Nữ','15/09/2002', 21, '0789123456','092128312021'),
(N'Phạm Thị Thanh Thảo', N'Nữ','20/10/2002', 22, '0891234567','092128312022'),
(N'Lê Thị Ngọc Hân', N'Nữ','25/11/2002', 1, '0912345678','092128312023'),
(N'Võ Thị Mỹ Dung', N'Nữ','30/12/2002', 2, '0123456789','092128312024'),
(N'Nguyễn Thị Thanh Hằng', N'Nữ','05/01/2003', 3, '0234567891','092128312025'),
(N'Trần Thị Kim Ngọc', N'Nữ','10/02/2003', 4, '0345678912','092128312026'),
(N'Phạm Thị Minh Tâm', N'Nữ','15/03/2003', 5, '0456789123','092128312027'),
(N'Lê Thị Thu Hằng', N'Nữ','20/04/2003', 6, '0567891234','092128312028'),
(N'Võ Thị Ngọc Mai', N'Nữ','25/05/2003', 7, '0678912345','092128312029'),
(N'Nguyễn Thị Thanh Nga', N'Nữ','30/06/2003', 8, '0789123456','092128312030'),
(N'Nguyễn Thị Hồng Nhung', N'Nữ','13/07/2003', 9, '0891234567','092128312031'),
(N'Trần Thị Mỹ Dung', N'Nữ','01/08/2003', 10, '0902345678','092128312032'),
(N'Phạm Thị Thanh Hằng', N'Nữ','05/09/2003', 11, '0913456789','092128312033'),
(N'Lê Thị Kim Ngân', N'Nữ','06/10/2003', 12, '0924567890','092128312034'),
(N'Võ Thị Minh Khai', N'Nữ','04/11/2003', 13, '0935678901','092128312035'),
(N'Nguyễn Thị Hoàng Oanh', N'Nữ','10/12/2003', 14, '0946789012','092128312036'),
(N'Trần Thị Thu Thảo', N'Nữ','15/01/2004', 15, '0957890123','092128312037'),
(N'Phạm Thị Hồng Nhung', N'Nữ','20/02/2004', 16, '0968901234','092128312038'),
(N'Lê Thị Mỹ Dung', N'Nữ','25/03/2004', 17, '0979012345','092128312039'),
(N'Võ Thị Thanh Hằng', N'Nữ','30/04/2004', 18, '0980123456','092128312040'),
(N'Nguyễn Thị Kim Ngân', N'Nữ','05/05/2004', 19, '0991234567','092128312041'),
(N'Trần Thị Minh Khai', N'Nữ','10/06/2004', 20, '0902345678','092128312042'),
(N'Phạm Thị Hoàng Oanh', N'Nữ','15/07/2004', 21, '0913456789','092128312043'),
(N'Lê Thị Thu Thảo', N'Nữ','20/08/2004', 22, '0924567890','092128312044'),
(N'Võ Thị Hồng Nhung', N'Nữ','25/09/2004', 1, '0935678901','092128312045'),
(N'Nguyễn Thị Mỹ Dung', N'Nữ','30/10/2004', 2, '0946789012','092128312046'),
(N'Trần Thị Thanh Hằng', N'Nữ','04/11/2004',3, '0957890123','092128312047'),
(N'Phạm Thị Kim Ngân', N'Nữ','10/12/2004', 4, '0968901234','092128312048'),
(N'Lê Thị Minh Khai', N'Nữ','15/01/2005', 5, '0979012345','092128312049'),
(N'Võ Thị Hoàng Oanh', N'Nữ','20/02/2005', 6, '0980123456','092128312050');

insert into LoaiPhong(TenLoaiPhong,DonGia) values
(N'Standard',1000000),
(N'Superior ',2000000),
(N'Deluxe ',3000000),	
(N'Suite ',4000000);



Insert into Phong(TenPhong, MaLoaiPhong, TienNghi, TrangThai) values
(N'Phòng Standard One',1, N'Tiện nghi cao cấp, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Standard Two',1, N'Tiện nghi cao cấp, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Superior One',2, N'Tiện nghi gia đình, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Superior Two',2,  N'Tiện nghi đôi, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Deluxe One', 3, N'Tiện nghi Twin, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Deluxe Two',3, N'Tiện nghi hạng sang, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Suite One',4, N'Tiện nghi đặc biệt, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Suite Two',4, N'Tiện nghi đôi, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Superior Three',2, N'Tiện nghi Twin, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống'),
(N'Phòng Standard Three', 1, N'Tiện nghi gia đình, Wi-Fi miễn phí, dịch vụ phòng 24/7', N'Đang trống');

Insert into LoaiDichVu(TenLoaiDV) values
(N'Dịch vụ phòng'),
(N'Dịch vụ ăn uống'),
(N'Dịch vụ giặt ủi'),
(N'Dịch vụ vận chuyển'),
(N'Dịch vụ spa và massage'),
(N'Dịch vụ thể thao và giải trí'),
(N'Dịch vụ y tế');
select * from hoadon
--Insert into DatPhong(NgayDatPhong, NgayTraPhong, SoNguoi, MaKH, MaPhong) values
--('01/01/2024', '03/01/2024', 2, 41, 1),
--('02/02/2024', '04/02/2024', 1, 22, 1),
--('03/03/2024', '06/03/2024', 3, 37, 5),
--('04/04/2024', '07/04/2024', 2, 15, 5),
--('05/05/2024', '08/05/2024', 1, 26, 6),
--('06/06/2024', '09/06/2024', 2, 35, 5),
--('07/07/2024', '10/07/2024', 3, 19, 9),
--('08/08/2024', '11/08/2024', 1, 32, 9),
--('09/09/2024', '12/09/2024', 2, 31, 5),
--('10/10/2024', '13/10/2024', 3, 20, 6),
--('11/11/2024', '14/11/2024', 1, 46, 7),
--('12/12/2024', '15/12/2024', 2, 34, 7),
--('13/01/2024', '16/01/2024', 3, 23, 4),
--('14/02/2024', '17/02/2024', 1, 50, 3),
--('15/03/2024', '18/03/2024', 2, 39, 8),
--('16/04/2024', '19/04/2024', 3, 29, 4),
--('17/05/2024', '20/05/2024', 1, 48, 6),
--('18/06/2024', '21/06/2024', 2, 44, 2),
--('19/07/2024', '22/07/2024', 3, 36, 6),
--('20/08/2024', '23/08/2024', 1, 40, 1);


Insert into DichVu(TenDV, DonGia, MaLoaiDV) values
(N'Dịch vụ phòng cao cấp', 500000, 1),
(N'Bữa sáng buffet', 200000, 2),
(N'Dịch vụ giặt ủi hàng ngày', 100000, 3),
(N'Dịch vụ đưa đón sân bay', 300000, 4),
(N'Gói massage thư giãn', 600000, 5),
(N'Thuê xe đạp', 100000, 6),
(N'Khám sức khỏe tổng quát', 1000000, 7),
(N'Dịch vụ phòng tầm nhìn đẹp', 600000, 1),
(N'Bữa tối lãng mạn', 400000, 2),
(N'Dịch vụ giặt ủi nhanh', 200000, 3);

--Insert into PhucVu(SoLuong, MaKH, MaDV) values 
--(2, 41, 7),
--(5, 22, 8),
--(1, 37, 5),
--(3, 15, 2),
--(4, 26, 10),
--(2, 35, 3),
--(1, 19, 9),
--(2, 32, 9),
--(3, 31, 9),
--(5, 20, 10),
--(4, 46, 1),
--(1, 34, 4),
--(2, 23, 6),
--(3, 50, 7),
--(4, 39, 8),
--(1, 29, 9),
--(2, 48, 10),
--(3, 44, 1),
--(1, 36, 2),
--(4, 40, 3);

--Insert into HoaDon(NgayLapHD, MaNV, MaKH, MaPhong) values
--('01/01/2024', 1, 41, 1),
--('02/02/2024', 2, 22, 1),
--('03/03/2024', 3, 37, 5),
--('04/04/2024', 1, 15, 5),
--('05/05/2024', 2, 26, 6),
--('06/06/2024', 3, 35, 5),
--('07/07/2024', 1, 19, 9),
--('08/08/2024', 2, 32, 9),
--('09/09/2024', 3, 31, 5),
--('10/10/2024', 1, 20, 6),
--('11/11/2024', 2, 46, 7),
--('12/12/2024', 3, 34, 7),
--('13/01/2024', 1, 23, 4),
--('14/02/2024', 2, 50, 3),
--('15/03/2024', 3, 39, 8),
--('16/04/2024', 1, 29, 4),
--('17/05/2024', 2, 48, 6),
--('18/06/2024', 3, 44, 2),
--('19/07/2024', 1, 36, 6),
--('20/08/2024', 2, 40, 1);

select * from HoaDon;


select * from KhachHang;

select * from DatPhong

delete HoaDon
delete DatPhong
delete PhucVu