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