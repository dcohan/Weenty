/****** Object:  Table [dbo].[subcategory]    Script Date: 01/14/2015 22:08:48 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[subcategory](
	[IdSubCategory] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[IdCategory] [int] NOT NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_subcategory] PRIMARY KEY CLUSTERED 
(
	[IdSubCategory] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[subcategory]  WITH CHECK ADD FOREIGN KEY([IdCategory])
REFERENCES [dbo].[category] ([IdCategory])
GO

/****** Object:  Table [dbo].[storeCategory]    Script Date: 01/14/2015 22:09:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[storeCategory](
	[IdStoreCategory] [int] IDENTITY(1,1) NOT NULL,
	[IdStore] [int] NOT NULL,
	[IdCategory] [int] NULL,
	[IdSubCategory] [int] NULL,
	[CreationDatetime] [datetime] NULL,
	[ModificationDatetime] [datetime] NULL,
	[DeletionDatetime] [datetime] NULL,
 CONSTRAINT [PK_storeCategory] PRIMARY KEY CLUSTERED 
(
	[IdStoreCategory] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[storeCategory]  WITH CHECK ADD  CONSTRAINT [FK_storeCategory_category] FOREIGN KEY([IdCategory])
REFERENCES [dbo].[category] ([IdCategory])
GO

ALTER TABLE [dbo].[storeCategory] CHECK CONSTRAINT [FK_storeCategory_category]
GO

ALTER TABLE [dbo].[storeCategory]  WITH CHECK ADD  CONSTRAINT [FK_storeCategory_store] FOREIGN KEY([IdStore])
REFERENCES [dbo].[store] ([IdStore])
GO

ALTER TABLE [dbo].[storeCategory] CHECK CONSTRAINT [FK_storeCategory_store]
GO

ALTER TABLE [dbo].[storeCategory]  WITH CHECK ADD  CONSTRAINT [FK_storeCategory_subcategory] FOREIGN KEY([IdSubCategory])
REFERENCES [dbo].[subcategory] ([IdSubCategory])
GO

ALTER TABLE [dbo].[storeCategory] CHECK CONSTRAINT [FK_storeCategory_subcategory]
GO


ALTER TABLE store DROP COLUMN IdCategory
GO

ALTER TABLE category ADD ImagePath varchar(250) NULL
GO



-----------------------------


GO
/****** Object:  StoredProcedure [dbo].[GetNearestStoresWithOffers]    Script Date: 01/15/2015 14:44:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetNearestStoresWithOffers]
	@Latitude float, 
	@Longitude float
AS
BEGIN
	DECLARE @StoresWithOffers TABLE (IdStore INT)
	
	INSERT INTO @StoresWithOffers 
		SELECT DISTINCT p.IdStore
		FROM product p
		LEFT JOIN offer o ON o.IdProduct = p.IdProduct
		WHERE p.DeletionDatetime IS NULL 
			AND p.StartDatetime <= GETDATE()
			AND (
				p.ExpirationDatetime > GETDATE() 
				OR 
				p.ExpirationDatetime IS NULL
			)
			AND o.DeletionDatetime IS NULL
			AND o.StartDatetime <= GETDATE()
			AND (
				o.ExpirationDatetime > GETDATE() 
				OR 
				o.ExpirationDatetime IS NULL
			);
			
	SELECT s.*,
		   dbo.Distance(s.Latitude, s.Longitude, @Latitude, @Longitude) as Distance,
		   CASE  
			WHEN swp.IdStore IS NULL THEN 0
			ELSE 1 
		   END AS HasOffers
	FROM store s 
	INNER JOIN company c ON c.IdCompany = s.IdCompany
	INNER JOIN companySubscription cs  ON cs.IdCompany = c.IdCompany
	INNER JOIN subscription su ON su.IdSubscription = cs.IdSubscription
	LEFT JOIN @StoresWithOffers swp ON swp.IdStore = s.IdStore
	WHERE s.DeletionDatetime IS NULL 
		AND c.DeletionDatetime IS NULL 
		AND cs.DeletionDateTime IS NULL 
		AND cs.EndDate >= GETDATE() 
		AND su.DeletionDateTime IS NULL
		AND swp.IdStore IS NOT NULL
	ORDER BY su.SortFactor, Distance ASC

END

---------------------------------------------------


GO
/****** Object:  StoredProcedure [dbo].[GetNearestStoresByName]    Script Date: 01/15/2015 14:55:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetNearestStoresByName] (@IdCategory int, @Latitude float, @Longitude float, @Name nvarchar(30))
AS

	DECLARE @StoresWithProducts TABLE (IdStore INT)
	
	INSERT INTO @StoresWithProducts 
		SELECT DISTINCT p.IdStore
		FROM product p 
		WHERE p.DeletionDatetime IS NULL 
			AND p.StartDatetime <= GETDATE()
			AND (
				p.ExpirationDatetime > GETDATE() 
				OR 
				p.ExpirationDatetime IS NULL
			);

	SELECT DISTINCT s.*,
	su.SortFactor,
		dbo.Distance(s.Latitude, s.Longitude, @Latitude, @Longitude) as Distance,
		CASE WHEN (
			SELECT COUNT(1)
			FROM offer o
			INNER JOIN product p ON o.IdProduct = p.IdProduct
			WHERE p.IdStore = swp.IdStore
				AND o.DeletionDatetime IS NULL
				AND o.StartDatetime <= GETDATE()
				AND (
					o.ExpirationDatetime > GETDATE() 
					OR 
					o.ExpirationDatetime IS NULL
				) 
			) > 0 THEN 1
			ELSE 0
		END AS HasOffers, 
		CASE WHEN swp.IdStore IS NULL THEN 0
			ELSE 1
		END	AS HasProducts
	FROM store s 
	INNER JOIN company c ON c.IdCompany = s.IdCompany
	INNER JOIN companySubscription cs  ON cs.IdCompany = c.IdCompany
	INNER JOIN subscription su ON su.IdSubscription = cs.IdSubscription
	INNER JOIN storeCategory sc ON s.IdStore = sc.IdStore
	LEFT JOIN @StoresWithProducts swp ON swp.IdStore = s.IdStore
	WHERE s.DeletionDatetime IS NULL 
		AND c.DeletionDatetime IS NULL 
		AND cs.DeletionDateTime IS NULL 
		AND cs.EndDate >= GETDATE() 
		AND su.DeletionDateTime IS NULL
		AND LOWER(s.Name) LIKE '%' + LOWER(@Name) + '%'
		AND sc.DeletionDateTime IS NULL
		AND (
			sc.IdCategory IS NOT NULL
			AND sc.IdCategory = @IdCategory
		 OR 
			sc.IdCategory IS NULL
			AND sc.IdSubCategory IS NOT NULL
			AND EXISTS(SELECT 1 FROM subcategory AS subc WHERE subc.IdSubCategory = sc.IdSubCategory AND subc.IdCategory = @IdCategory)
		)
	ORDER BY su.SortFactor, Distance ASC

	
	--------------------------------------------------------------------------

GO
/****** Object:  StoredProcedure [dbo].[GetNearestStores]    Script Date: 01/15/2015 14:56:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetNearestStores] (@IdCategory int, @Latitude float, @Longitude float)
AS
	DECLARE @StoresWithProducts TABLE (IdStore INT)
	
	INSERT INTO @StoresWithProducts 
		SELECT DISTINCT p.IdStore
		FROM product p 
		WHERE p.DeletionDatetime IS NULL 
			AND p.StartDatetime <= GETDATE()
			AND (
				p.ExpirationDatetime > GETDATE() 
				OR 
				p.ExpirationDatetime IS NULL
			)
			
	SELECT DISTINCT s.*,
		su.SortFactor,
		dbo.Distance(s.Latitude, s.Longitude, @Latitude, @Longitude) as Distance,
		CASE WHEN (SELECT COUNT(1)
		FROM offer o
		INNER JOIN product p ON o.IdProduct = p.IdProduct
		WHERE p.IdStore = swp.IdStore
			AND o.DeletionDatetime IS NULL
			AND o.StartDatetime <= GETDATE()
			AND (
				o.ExpirationDatetime > GETDATE() 
				OR 
				o.ExpirationDatetime IS NULL
			) 
		) > 0 THEN 1
		ELSE 0
		END AS HasOffers,
		CASE WHEN swp.IdStore IS NULL THEN 0
			ELSE 1
		END	AS HasProducts /* If is in the other table it means that has products */
	FROM store s 
	INNER JOIN company c ON c.IdCompany = s.IdCompany
	INNER JOIN companySubscription cs  ON cs.IdCompany = c.IdCompany
	INNER JOIN subscription su ON su.IdSubscription = cs.IdSubscription
	INNER JOIN storeCategory sc ON s.IdStore = sc.IdStore
	LEFT JOIN @StoresWithProducts swp ON swp.IdStore = s.IdStore
	WHERE s.DeletionDatetime IS NULL 
		AND c.DeletionDatetime IS NULL 
		AND cs.DeletionDateTime IS NULL 
		AND cs.EndDate >= GETDATE() 
		AND su.DeletionDateTime IS NULL
		AND sc.DeletionDateTime IS NULL
		AND (
			sc.IdCategory IS NOT NULL
			AND sc.IdCategory = @IdCategory
		 OR 
			sc.IdCategory IS NULL
			AND sc.IdSubCategory IS NOT NULL
			AND EXISTS(SELECT 1 FROM subcategory AS subc WHERE subc.IdSubCategory = sc.IdSubCategory AND subc.IdCategory = @IdCategory)
		)
	ORDER BY su.SortFactor, Distance ASC

	----------------------------------------------------------------

GO
/****** Object:  StoredProcedure [dbo].[GetProductAndOffers]    Script Date: 01/15/2015 14:56:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GetProductAndOffers] (@IdStore int)
AS

SELECT p.Title as PTitle, p.ImagePath as PImagePath, p.Description as PDescription,
	p.IdProduct as IdProduct, p.Price as PPrice,
	o.Title as OTitle, o.ImagePath as OImagePath, o.IdOffer as IdOffer, o.Price as OPrice, o.Description as ODescription
FROM product p 
LEFT JOIN offer o ON p.IdProduct = o.IdProduct
INNER JOIN store s ON s.IdStore = p.IdStore
WHERE @IdStore = s.IdStore 
	AND p.DeletionDatetime IS NULL 
	AND (
		(o.Title IS NOT NULL AND o.DeletionDatetime IS NULL AND o.IdOffer IS NOT NULL)
		OR
		o.Title IS NULL
	)
and p.StartDatetime <= GETDATE() and (GETDATE() <= p.ExpirationDatetime or p.ExpirationDatetime is null)
and o.StartDatetime <= GETDATE() and (GETDATE() <= o.ExpirationDatetime or o.ExpirationDatetime is null)
ORDER BY O.Title ASC, P.Title ASC
