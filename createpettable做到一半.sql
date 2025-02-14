USE [meowdb]
GO

/****** Object:  Table [dbo].[Activity]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/****** Object:  Table [dbo].[Admin]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Admin](
	[adminId] [int] IDENTITY(1,1) NOT NULL,
	[createDate] [datetime2](6) NOT NULL,
	[updateDate] [datetime2](6) NOT NULL,
	[adminName] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[adminId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK20am2dw7hveof92n93yj0jslb] UNIQUE NONCLUSTERED 
(
	[adminName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Member]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Member](
	[birthday] [date] NOT NULL,
	[followed] [bit] NOT NULL,
	[memberId] [int] IDENTITY(1,1) NOT NULL,
	[userType] [bit] NOT NULL,
	[createDate] [datetime2](6) NOT NULL,
	[updateDate] [datetime2](6) NOT NULL,
	[phone] [varchar](10) NOT NULL,
	[nickName] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[name] [varchar](70) NOT NULL,
	[address] [varchar](100) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[lineId] [varchar](255) NULL,
	[lineName] [varchar](255) NULL,
	[linePicture] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[memberId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK9qv6yhjqm8iafto8qk452gx8h] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UKbi8025k604cnxv1vg6fvkd9fb] UNIQUE NONCLUSTERED 
(
	[nickName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


/****** Object:  Table [dbo].[City]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[City](
	[cityId] [int] IDENTITY(1,1) NOT NULL,
	[city] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cityId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]



/****** Object:  Table [dbo].[DistrictArea]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DistrictArea](
	[cityId] [int] NOT NULL,
	[districtAreaId] [int] IDENTITY(1,1) NOT NULL,
	[districtAreaName] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[districtAreaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO



/****** Object:  Table [dbo].[Species]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Species](
	[speciesId] [int] IDENTITY(1,1) NOT NULL,
	[species] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[speciesId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[Breed]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Breed](
	[breedId] [int] IDENTITY(1,1) NOT NULL,
	[breed] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[breedId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


/****** Object:  Table [dbo].[FurColor]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[FurColor](
	[furColorId] [int] IDENTITY(1,1) NOT NULL,
	[furColor] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[furColorId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[CaseState]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CaseState](
	[CaseStateId] [int] IDENTITY(1,1) NOT NULL,
	[CaseStatement] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CaseStateId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[CanAfford]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CanAfford](
	[canAffordId] [int] IDENTITY(1,1) NOT NULL,
	[canAfford] [nvarchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[canAffordId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO



/****** Object:  Table [dbo].[RescueDemand]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[RescueDemand](
	[rescueDemandId] [int] IDENTITY(1,1) NOT NULL,
	[rescueDemand] [nvarchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[rescueDemandId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[AdoptionCase]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AdoptionCase](
	[adoptionCaseId] [int] IDENTITY(1,1) NOT NULL,
	[age] [int] NULL,
	[breedId] [int] NOT NULL,
	[caseStateId] [int] NOT NULL,
	[cityId] [int] NOT NULL,
	[districtAreaId] [int] NOT NULL,
	[follow] [int] NULL,
	[furColorId] [int] NOT NULL,
	[latitude] [numeric](10, 8) NULL,
	[longitude] [numeric](11, 8) NULL,
	[memberId] [int] NOT NULL,
	[microChipNumber] [int] NULL,
	[speciesId] [int] NOT NULL,
	[susLost] [bit] NOT NULL,
	[viewCount] [int] NULL,
	[lastUpdateTime] [datetime2](6) NOT NULL,
	[publicationTime] [datetime2](6) NOT NULL,
	[adoptedCondition] [nvarchar](max) NOT NULL,
	[caseTitle] [nvarchar](30) NOT NULL,
	[gender] [nvarchar](10) NULL,
	[healthCondition] [nvarchar](max) NOT NULL,
	[note] [nvarchar](max) NULL,
	[sterilization] [nvarchar](5) NULL,
	[story] [nvarchar](max) NOT NULL,
	[street] [nvarchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[adoptionCaseId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


/****** Object:  Table [dbo].[LostCase]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[LostCase](
	[age] [int] NULL,
	[breedId] [int] NULL,
	[caseStateId] [int] NOT NULL,
	[cityId] [int] NOT NULL,
	[districtAreaId] [int] NOT NULL,
	[donationAmount] [int] NULL,
	[follow] [int] NULL,
	[furColorId] [int] NULL,
	[isHidden] [bit] NOT NULL,
	[latitude] [float] NULL,
	[longitude] [float] NULL,
	[lostCaseId] [int] IDENTITY(1,1) NOT NULL,
	[memberId] [int] NULL,
	[microChipNumber] [int] NULL,
	[speciesId] [int] NOT NULL,
	[viewCount] [int] NULL,
	[lastUpdateTime] [datetime2](6) NOT NULL,
	[publicationTime] [datetime2](6) NOT NULL,
	[caseTitle] [nvarchar](30) NOT NULL,
	[caseUrl] [varchar](255) NULL,
	[gender] [nvarchar](5) NULL,
	[petName] [nvarchar](5) NULL,
	[sterilization] [nvarchar](5) NOT NULL,
	[street] [nvarchar](10) NULL,
	[contactInformation] [varchar](max) NULL,
	[featureDescription] [varchar](max) NOT NULL,
	[lostExperience] [varchar](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[lostCaseId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  Table [dbo].[RescueCase]    Script Date: 2025/2/14 下午 02:07:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[RescueCase](
	[age] [int] NULL,
	[breedId] [int] NULL,
	[caseStateId] [int] NOT NULL,
	[cityId] [int] NOT NULL,
	[districtAreaId] [int] NOT NULL,
	[donationAmount] [int] NULL,
	[follow] [int] NULL,
	[furColorId] [int] NULL,
	[isHidden] [bit] NOT NULL,
	[latitude] [float] NOT NULL,
	[longitude] [float] NOT NULL,
	[memberId] [int] NOT NULL,
	[microChipNumber] [int] NULL,
	[rescueCaseId] [int] IDENTITY(1,1) NOT NULL,
	[speciesId] [int] NOT NULL,
	[suspLost] [bit] NOT NULL,
	[viewCount] [int] NULL,
	[lastUpdateTime] [datetime2](6) NOT NULL,
	[publicationTime] [datetime2](6) NOT NULL,
	[caseTitle] [nvarchar](30) NOT NULL,
	[caseUrl] [varchar](255) NULL,
	[gender] [nvarchar](5) NULL,
	[rescueReason] [nvarchar](max) NOT NULL,
	[sterilization] [nvarchar](5) NULL,
	[street] [nvarchar](10) NULL,
	[tag] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[rescueCaseId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO